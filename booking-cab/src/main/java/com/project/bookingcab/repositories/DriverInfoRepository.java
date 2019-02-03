

package com.project.bookingcab.repositories;

import com.project.bookingcab.model.DriverInfo;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

/**
 * @author nitish on 2019-02-02.
 */
public interface DriverInfoRepository extends CassandraRepository<DriverInfo> {
  @Query("select * from carbooking.driver_info where driver_id =?0")
  DriverInfo getByDriverId(String driverId);
}
