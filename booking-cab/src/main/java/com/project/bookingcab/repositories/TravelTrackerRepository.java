

package com.project.bookingcab.repositories;

import com.project.bookingcab.model.TravelTracker;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

/**
 * @author nitish on 2019-02-02.
 */
public interface TravelTrackerRepository extends CassandraRepository<TravelTracker> {

  @Query("select * from carbooking.travel_tracker where travel_id = ?0;")
  TravelTracker getByTravelId(String travelId);
}
