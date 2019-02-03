

package com.project.bookingcab.repositories;

import com.project.bookingcab.model.UserTracker;
import java.util.stream.Stream;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

/**
 * @author nitish on 2019-02-02.
 */
public interface UserTrackerRepository extends CassandraRepository<UserTracker> {

  @Query("select * From carbooking.user_tracker where uid = ?0")
  Stream<UserTracker> getByUserId(String userId);

  @Query("select * from carbooking.user_tracker where uid = ?0 and travel_id =?1")
  Stream<UserTracker> getByUidAndUserId(String userId, String travelId);
}
