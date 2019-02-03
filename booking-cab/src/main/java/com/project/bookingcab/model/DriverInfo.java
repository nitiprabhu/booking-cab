

package com.project.bookingcab.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @author nitish on 2019-02-02.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(value = "driver_info")
public class DriverInfo {
  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
  private String driverId;

  private String driverEmail;

  private String driverName;

  private String driverPhone;
}
