

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
@Table(value = "user_info")
public class UserInfo {
  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
  private String uid;

  private String fname;

  private String lname;

  private String gender; //ideally, enum should be used

  private String phone;

  private String email;

}
