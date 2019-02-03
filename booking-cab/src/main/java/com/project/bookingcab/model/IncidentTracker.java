

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
@Table(value = "incident_tracker")
public class IncidentTracker {
  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED, name = "incident_id")
  private String incidentId;

  @Column(value = "travel_id")
  private String travelId;

  private String status;

  private String uid;

  private String incident;
}
