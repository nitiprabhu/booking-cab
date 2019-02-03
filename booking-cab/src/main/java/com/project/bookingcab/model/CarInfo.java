

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
@Table(value = "car_info")
public class CarInfo {
  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "car_type")
  private String carType;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 0, name = "car_name")
  private String carName;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 1, name = "car_id")
  private String carId;

  @Column(value = "car_number")
  private String carNumber;

  @Column(value = "price_factor")
  private double priceFactor;
}
