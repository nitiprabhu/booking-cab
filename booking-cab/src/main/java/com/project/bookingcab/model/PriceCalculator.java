

package com.project.bookingcab.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @author nitish on 2019-02-02.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(value = "price_calculator")
public class PriceCalculator {
  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
  private String source;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
  private String destination;

  private int distance;

  @Column(value = "regular_price")
  private double regularPrice;


}
