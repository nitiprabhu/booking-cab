

package com.project.bookingcab.model.restmodels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nitish on 2019-02-03.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JourneyStartStatus {
  private String travelId;
  private String startTime;
  private BookedCar bookedCar;
  private String status;
}
