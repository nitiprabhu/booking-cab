

package com.project.bookingcab.repositories;

import com.project.bookingcab.model.UserInfo;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

/**
 * @author nitish on 2019-02-02.
 */
public interface UserInfoRepository extends CassandraRepository<UserInfo> {
  @Query("select * from carbooking.user_info where uid = ?0")
  UserInfo getByUid(String uid);
}
