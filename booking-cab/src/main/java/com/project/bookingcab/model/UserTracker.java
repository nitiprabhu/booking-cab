

package com.project.bookingcab.model;

import java.time.Instant;
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
@Table(value = "user_tracker")
public class UserTracker {
  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
  private String uid;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, name = "travel_id")
  private String travelId;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordinal = 1, name = "last_updated")
  private Instant lastUpdated;

  @Column("car_id")
  private String carId;

  @Column("journey_status")
  private String journeyStatus;

}
