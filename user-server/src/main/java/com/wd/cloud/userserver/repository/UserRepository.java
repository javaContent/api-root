package com.wd.cloud.userserver.repository;

import com.wd.cloud.userserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author He Zhigang
 * @date 2018/6/5
 * @Description:
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPwd(String username, String pwd);
}
