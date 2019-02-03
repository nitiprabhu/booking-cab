

package com.project.bookingcab.repositories;

import com.project.bookingcab.model.CarInfo;
import java.util.stream.Stream;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

/**
 * @author nitish on 2019-02-02.
 */
public interface CarInfoRepository extends CassandraRepository<CarInfo> {

  @Query("select * from carbooking.car_info where car_type = ?0")
  Stream<CarInfo> getByCarType(String carType);

  @Query("select * from carbooking.car_info")
  Stream<CarInfo> getAll();
}
