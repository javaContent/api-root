package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.domain.HelpRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description:
 */
public interface HelpRecordRepository extends JpaRepository<HelpRecord,Integer>{

    List<HelpRecord> findByEmail(String email);
    
    HelpRecord findById(Long id);
    
    HelpRecord findByIdAndStatus(Long id,int status);
    
    @Transactional
    @Modifying
    @Query("update HelpRecord hr set hr.process_type = :processType ,hr.process_id = :processId ,hr.status = :status ,hr.give_user_id = :giveUserId ,hr.doc_filename = :docFilename where sc.id = :id")
    public void update(@Param(value = "helpRecord") HelpRecord helpRecord);
    
    
}
