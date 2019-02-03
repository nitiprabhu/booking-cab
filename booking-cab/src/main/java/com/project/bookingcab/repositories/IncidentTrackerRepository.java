

package com.project.bookingcab.repositories;

import com.project.bookingcab.model.IncidentTracker;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

/**
 * @author nitish on 2019-02-02.
 */
public interface IncidentTrackerRepository extends CassandraRepository<IncidentTracker> {
  @Query("select * from carbooking.incident_tracker where incident_id = 0?;")
  IncidentTracker getByIncidentId(String incidentId);
}
