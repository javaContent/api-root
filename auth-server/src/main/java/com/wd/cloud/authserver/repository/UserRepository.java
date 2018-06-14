package com.wd.cloud.authserver.repository;

import com.wd.cloud.authserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author He Zhigang
 * @date 2018/6/5
 * @Description:
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPwd(String username, String pwd);
}
