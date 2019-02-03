

package com.project.bookingcab.service;

import com.project.bookingcab.model.*;
import com.project.bookingcab.model.restmodels.AvailableCarDetails;
import com.project.bookingcab.model.restmodels.BookedCar;
import com.project.bookingcab.model.restmodels.CompleteJourney;
import com.project.bookingcab.model.restmodels.JourneyStartStatus;
import com.project.bookingcab.repositories.*;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nitish on 2019-02-02.
 */
@Service
public class CarBookingService {
  private static final String BOOKED = "BOOKED";
  private static final String CANCELLED = "CANCELLED";
  private static final String END_JOURNEY = "END_JOURNEY";
  private static final String CREATED = "CREATED";
  private final CarInfoRepository carInfoRepository;
  private final DriverInfoRepository driverInfoRepository;
  private final IncidentTrackerRepository incidentTrackerRepository;
  private final PriceCalculatorRepository priceCalculatorRepository;
  private final TravelTrackerRepository travelTrackerRepository;
  private final UserInfoRepository userInfoRepository;
  private final UserTrackerRepository userTrackerRepository;

  @Autowired
  public CarBookingService(CarInfoRepository carInfoRepository,
                           DriverInfoRepository driverInfoRepository,
                           IncidentTrackerRepository incidentTrackerRepository,
                           PriceCalculatorRepository priceCalculatorRepository,
                           TravelTrackerRepository travelTrackerRepository,
                           UserInfoRepository userInfoRepository,
                           UserTrackerRepository userTrackerRepository) {
    this.carInfoRepository = carInfoRepository;
    this.driverInfoRepository = driverInfoRepository;
    this.incidentTrackerRepository = incidentTrackerRepository;
    this.priceCalculatorRepository = priceCalculatorRepository;
    this.travelTrackerRepository = travelTrackerRepository;
    this.userInfoRepository = userInfoRepository;
    this.userTrackerRepository = userTrackerRepository;
  }

  /**
   * Method to get an available cardetails based on the source and car's availability.
   *
   * @param source      source of the user
   * @param destination destination of the user
   * @return available cardetails information
   */
  public Optional<AvailableCarDetails> getAvailableCarWithPriceDetails(String source,
                                                                       String destination) {
    Optional<PriceCalculator> sourceDestinationPriceCalculator = Optional.ofNullable(
        priceCalculatorRepository
            .getBySourceAndDestination(
                source,
                destination));

    if (!sourceDestinationPriceCalculator.isPresent()) {
      return Optional.empty();
    }

    PriceCalculator priceCalculator = sourceDestinationPriceCalculator.get();
    List<AvailableCarDetails.CarDetails> carDetails = getCarDetailsFromSource()
        .stream()
        .map(carInfo -> AvailableCarDetails.CarDetails.builder()
            .carId(carInfo.getCarId())
            .carType(carInfo.getCarType())
            .price(carInfo.getPriceFactor() * priceCalculator
                .getRegularPrice())
            .build())
        .collect(Collectors.toList());

    return Optional.of(AvailableCarDetails.builder()
                           .source(source)
                           .distance(priceCalculator.getDistance())
                           .destination(destination)
                           .carDetailsList(carDetails)
                           .build());


  }

  /**
   * Method to get a list of cars available based on user's & driver's source.
   * Assumption:- Currently this method is returning list of cars available in the db, assuming all cars defined in the db are available for booking.
   *
   * @return list of carinfo available
   */
  private List<CarInfo> getCarDetailsFromSource() {
    return carInfoRepository.getAll().collect(Collectors.toList());
  }

  public CompleteJourney completeJourney(JourneyStartStatus journeyStartStatus) {
    String travelId = journeyStartStatus.getTravelId();

    Optional<TravelTracker> travelIdOptional = Optional.ofNullable(travelTrackerRepository.getByTravelId(
        travelId));

    if (!travelIdOptional.isPresent()) {
      return new CompleteJourney(travelId, 1, "Travel Id is not present");
    }

    String existingJourneyStatus = userTrackerRepository.getByUidAndUserId(journeyStartStatus.getBookedCar()
                                                                               .getUid(), travelId)
        .findFirst()
        .map(UserTracker::getJourneyStatus)
        .orElse("");

    if (!BOOKED.equalsIgnoreCase(existingJourneyStatus)) {
      return new CompleteJourney(travelId, 1, "Journey is already ended");
    }

    UserTracker userTracker = UserTracker.builder()
        .carId(journeyStartStatus.getBookedCar().getCarId())
        .journeyStatus(journeyStartStatus.getStatus())
        .travelId(travelId)
        .uid(journeyStartStatus.getBookedCar().getUid())
        .lastUpdated(Instant.now())
        .build();


    TravelTracker travelTracker = travelIdOptional.get();
    travelTracker.setEndTime(LocalTime.now().toString());

    String ackMessage = MessageFormat.format("Your journey ended successfully, charge is {0}",
                                             journeyStartStatus.getBookedCar().getCost());


    if (CANCELLED.equalsIgnoreCase(journeyStartStatus.getStatus())) {
      //just an assumption - 30% cancellation charge will be charged
      double cancellationCharge = journeyStartStatus.getBookedCar().getCost() * 0.3;
      travelTracker.setCost(cancellationCharge);
      travelTracker.setDistanceCovered(0);
      ackMessage = MessageFormat.format(
          "Successfully cancelled the booking, your cancellation charge is {0}",
          cancellationCharge);
    }

    userTrackerRepository.insert(userTracker);
    travelTrackerRepository.insert(travelTracker);


    return new CompleteJourney(travelId, 0, ackMessage);


  }

  /**
   * Method that takes action when car is booked by the user
   *
   * @param bookedCar bookedCar details
   */
  public JourneyStartStatus bookCarAndTrackerId(BookedCar bookedCar) {

    String travelId = UUID.randomUUID().toString();

    UserTracker userTracker = UserTracker.builder()
        .carId(bookedCar.getCarId())
        .journeyStatus(BOOKED)
        .travelId(travelId)
        .uid(bookedCar.getUid())
        .lastUpdated(Instant.now())
        .build();

    String now = LocalTime.now().toString();
    TravelTracker travelTracker = TravelTracker.builder()
        .cost(bookedCar.getCost())
        .destination(bookedCar.getDestination())
        .source(bookedCar.getSource())
        .startTime(now)
        .travelId(travelId)
        .distanceCovered(bookedCar.getDistance())
        .build();

    userTrackerRepository.insert(userTracker);
    travelTrackerRepository.insert(travelTracker);

    return new JourneyStartStatus(travelId, now, bookedCar, BOOKED);
  }

  public List<TravelTracker> getUserTripsDetails(String uid) {

    return userTrackerRepository.getByUserId(uid)
        .map(UserTracker::getTravelId)
        .distinct()
        .map(travelTrackerRepository::getByTravelId)
        .collect(Collectors.toList());
  }

  public String createIncident(String uid, String travelId, String incident) {
    String incidentId = UUID.randomUUID().toString();
    IncidentTracker incidentTracker = IncidentTracker.builder()
        .uid(uid)
        .travelId(travelId)
        .incidentId(incidentId)
        .incident(incident)
        .status(CREATED)
        .build();

    incidentTrackerRepository.insert(incidentTracker);
    return incidentId;
  }
}
