package com.wd.cloud.authserver.repository;

import com.wd.cloud.authserver.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author He Zhigang
 * @date 2018/6/5
 * @Description:
 */
public interface SchoolRepository extends JpaRepository<School, Long> {
}
