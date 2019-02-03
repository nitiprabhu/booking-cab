

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
@Table(value = "travel_tracker")
public class TravelTracker {
  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "travel_id")
  private String travelId;

  private double cost;

  private String destination;

  @Column(value = "distance_covered")
  private int distanceCovered;

  @Column(value = "end_time")
  private String endTime;

  @Column(value = "start_time")
  private String startTime;

  private String source;
}
