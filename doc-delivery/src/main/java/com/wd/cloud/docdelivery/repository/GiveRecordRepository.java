package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.domain.GiveRecord;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    GiveRecord findByGiverIdAndAuditStatus(Long giverId,int auditStatus);

    GiveRecord findByHelpRecordAndAuditStatusAndGiverId(HelpRecord helpRecord,int auditStatus,long giverId);

    int deleteByHelpRecordAndAuditStatusAndGiverId(HelpRecord helpRecord,int auditStatus,long giverId);


    
    GiveRecord findByHelpRecordAndAuditStatusAndGiverType(HelpRecord helpRecord,int auditStatus,int giverType);
    
    @Query("from GiveRecord t where t.helpRecord = :helpRecord and (t.auditStatus = 1 or t.giverType <> 2)")
    GiveRecord findByHelpRecord(@Param("helpRecord") HelpRecord helpRecord);

}
