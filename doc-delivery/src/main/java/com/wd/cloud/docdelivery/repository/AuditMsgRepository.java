package com.wd.cloud.docdelivery.repository;

import com.wd.cloud.docdelivery.entity.AuditMsg;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author He Zhigang
 * @date 2018/5/31
 * @Description:
 */
public interface AuditMsgRepository extends JpaRepository<AuditMsg, Long> {


}
