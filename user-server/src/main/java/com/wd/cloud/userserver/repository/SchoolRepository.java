package com.wd.cloud.userserver.repository;

import com.wd.cloud.userserver.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author He Zhigang
 * @date 2018/6/5
 * @Description:
 */
public interface SchoolRepository extends JpaRepository<School, Long> {
}
