package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.enumeration.HelpStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description:
 */
public interface HelpRecordRepository extends JpaRepository<HelpRecord,Integer>{

    List<HelpRecord> findByHelpUserId(Integer helpUserId);
    List<HelpRecord> findByHelpUserId(Integer helpUserId,Pageable pageable);

    List<HelpRecord> findByHelpEmail(String helpEmail);
    List<HelpRecord> findByHelpEmail(String helpEmail,Pageable pageable);

    List<HelpRecord> findByStatus(Integer status, Pageable pageable);
}
