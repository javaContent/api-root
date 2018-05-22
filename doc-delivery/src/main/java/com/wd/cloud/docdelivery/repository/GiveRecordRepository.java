package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.domain.GiveRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author He Zhigang
 * @date 2018/5/22
 * @Description:
 */
public interface GiveRecordRepository extends JpaRepository<GiveRecord,Long> {

    /**
     * 查询待审核记录
     * @param id
     * @param auditStatus
     * @return
     */
    GiveRecord findByIdAndAuditStatus(Long id, int auditStatus);

}
