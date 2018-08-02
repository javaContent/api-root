package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.entity.GiveRecord;
import com.wd.cloud.docdelivery.entity.HelpRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/22
 * @Description:
 */
public interface GiveRecordRepository extends JpaRepository<GiveRecord, Long> {

    /**
     * 查询待审核记录
     *
     * @param id
     * @param auditStatus
     * @return
     */
    GiveRecord findByIdAndAuditStatus(Long id, int auditStatus);

    /**
     * 审核记录
     * @param giverId
     * @param auditStatus
     * @return
     */
    GiveRecord findByGiverIdAndAuditStatus(Long giverId, int auditStatus);

    /**
     * 特定状态的应助记录
     * @param helpRecord
     * @param auditStatus
     * @param giverId
     * @return
     */
    GiveRecord findByHelpRecordAndAuditStatusAndGiverId(HelpRecord helpRecord, int auditStatus, long giverId);

    /**
     * 取消应助，删除应助记录
     * @param helpRecord
     * @param auditStatus
     * @param giverId
     * @return
     */
    int deleteByHelpRecordAndAuditStatusAndGiverId(HelpRecord helpRecord, int auditStatus, long giverId);

    /**
     * 特定应助类型的应助记录
     * @param helpRecord
     * @param auditStatus
     * @param giverType
     * @return
     */
    GiveRecord findByHelpRecordAndAuditStatusAndGiverType(HelpRecord helpRecord, int auditStatus, int giverType);

    /**
     * 已审核通过的 或 非用户应助的应助记录
     * @param helpRecord
     * @return
     */
    @Query("FROM GiveRecord WHERE helpRecord = :helpRecord AND (auditStatus = 1 OR giverType <> 2)")
    GiveRecord findByHelpRecord(@Param("helpRecord") HelpRecord helpRecord);

    GiveRecord findByHelpRecordAndAuditStatusEquals(HelpRecord helpRecord,Integer status);

    @Query("FROM GiveRecord WHERE docFile IS NULL AND 15 < TIMESTAMPDIFF(MINUTE, gmtCreate, now())")
    List<GiveRecord> findTimeOutRecord();

    @Transactional
    @Modifying
    @Query("DELETE FROM GiveRecord WHERE docFile IS NULL AND 15 < TIMESTAMPDIFF(MINUTE, gmtCreate, now())")
    List<GiveRecord> deleteTimeOutRecord();

}
