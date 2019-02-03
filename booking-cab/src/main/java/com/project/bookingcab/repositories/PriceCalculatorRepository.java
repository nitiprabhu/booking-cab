

package com.project.bookingcab.repositories;

import com.project.bookingcab.model.PriceCalculator;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

/**
 * @author nitish on 2019-02-02.
 */
public interface PriceCalculatorRepository extends CassandraRepository<PriceCalculator> {

  @Query("select * from carbooking.price_calculator where source = ?0 and destination = ?1;")
  PriceCalculator getBySourceAndDestination(String source, String destination);
}
