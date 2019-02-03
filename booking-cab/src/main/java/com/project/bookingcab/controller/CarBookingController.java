

package com.project.bookingcab.controller;

import com.project.bookingcab.model.TravelTracker;
import com.project.bookingcab.model.restmodels.AvailableCarDetails;
import com.project.bookingcab.model.restmodels.BookedCar;
import com.project.bookingcab.model.restmodels.CompleteJourney;
import com.project.bookingcab.model.restmodels.JourneyStartStatus;
import com.project.bookingcab.service.CarBookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author nitish on 2019-02-02.
 */

@Api(description = "Controller for handling booking cabs")
@RestController
public class CarBookingController {
  private final CarBookingService carBookingService;

  @Autowired
  public CarBookingController(CarBookingService carBookingService) {
    this.carBookingService = carBookingService;
  }

  @ApiOperation(value = "fetch available cars based on source and destination",
                response = AvailableCarDetails.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200,
                   message = "Car Details Retrieved",
                   response = AvailableCarDetails.class),
      @ApiResponse(code = 204, message = "No Cars available")
  })
  @PostMapping("/fetch-cars")
  public ResponseEntity<?> fetchCars(@RequestParam String source,
                                     @RequestParam String destination) {
    return carBookingService.getAvailableCarWithPriceDetails(source, destination)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.noContent().build());
  }


  @ApiOperation(value = "book a car",
                response = JourneyStartStatus.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200,
                   message = "Successfully booked the car",
                   response = JourneyStartStatus.class),
      @ApiResponse(code = 500, message = "Failed to book the car")
  })
  @PostMapping("/book-car")
  public ResponseEntity<?> bookCars(@RequestBody BookedCar bookedCar) {
    try {
      JourneyStartStatus journeyStartStatus = carBookingService.bookCarAndTrackerId(bookedCar);
      return ResponseEntity.accepted().body(journeyStartStatus);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to book the car");
    }
  }

  @ApiOperation(value = "end a journey, pass 'CANCELLED' if user cancel the booking, 'END_JOURNEY' if succesfully ended the travel",
                response = CompleteJourney.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200,
                   message = "Successfully booked the car",
                   response = CompleteJourney.class),
      @ApiResponse(code = 500, message = "Failed to book the car")
  })
  @PostMapping("/end-journey")
  public CompleteJourney cancelBooking(@RequestBody JourneyStartStatus journeyStartStatus) {
    return carBookingService.completeJourney(journeyStartStatus);
  }

  @ApiOperation(value = "view trips",
                response = TravelTracker.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200,
                   message = "shows history of trips",
                   response = TravelTracker.class),
  })
  @GetMapping("/view-journey/{uid}")
  public List<TravelTracker> viewUserTrips(@PathVariable String uid) {
    return carBookingService.getUserTripsDetails(uid);
  }

  @ApiOperation(value = "report incident",
                response = String.class)
  @ApiResponses(value = {
      @ApiResponse(code = 200,
                   message = "incident id",
                   response = String.class),
  })
  @PostMapping("/report-incident")
  public String reportIncident(@RequestParam String uid,
                               @RequestParam String travelId,
                               @RequestParam String incident) {
    return carBookingService.createIncident(uid, travelId, incident);
  }


}
