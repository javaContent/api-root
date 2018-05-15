package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.domain.HelpRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description:
 */
public interface HelpRecordRepository extends JpaRepository<HelpRecord,Integer>{

    List<HelpRecord> findByEmail(String email);

}
