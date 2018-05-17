package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.domain.HelpRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description:
 */
public interface HelpRecordRepository extends JpaRepository<HelpRecord,Long>{

    /**
     * 查询
     * @param id
     * @param status
     * @return
     */
    HelpRecord findByIdAndStatus(Long id,int status);

//    @Transactional
//    @Modifying
//    @Query("update HelpRecord hr set hr.process_type = :processType ,hr.process_user_id = :processUserId ,hr.status = :status ,hr.give_user_id = :giveUserId ,hr.doc_filename = :docFilename where sc.id = :id")
//    void update(@Param(value = "helpRecord") HelpRecord helpRecord);

    /**
     * 根据求助用户ID查询
     * @param helpUserId
     * @return
     */
    List<HelpRecord> findByHelpUserId(Integer helpUserId);

    /**
     * 根据求助用户ID查询
     * @param helpUserId
     * @param pageable
     * @return
     */
    List<HelpRecord> findByHelpUserId(Integer helpUserId,Pageable pageable);

    /**
     * 根据求助邮箱查询
     * @param helpEmail
     * @return
     */
    List<HelpRecord> findByHelpEmail(String helpEmail);

    /**
     * 根据求助邮箱查询
     * @param helpEmail
     * @param pageable
     * @return
     */
    List<HelpRecord> findByHelpEmail(String helpEmail,Pageable pageable);

    /**
     * 根据互助状态查询
     * @param status
     * @param pageable
     * @return
     */
    List<HelpRecord> findByStatus(Integer status, Pageable pageable);
}
