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
@Data
@Builder
public class BookedCar {
  private String uid;
  private String source;
  private String destination;
  private String carId;
  private double cost;
  private int distance;
}
