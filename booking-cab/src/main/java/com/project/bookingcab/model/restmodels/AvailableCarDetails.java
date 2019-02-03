

package com.project.bookingcab.model.restmodels;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nitish on 2019-02-02.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AvailableCarDetails {
  private String source;
  private String destination;
  private int distance;
  private List<CarDetails> carDetailsList;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class CarDetails {
    private String carType;
    private String carId;
    private double price;


  }


}
