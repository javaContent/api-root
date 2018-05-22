package com.wd.cloud.docdelivery.service;

import java.util.Map;

import com.wd.cloud.docdelivery.domain.GiveRecord;
import org.springframework.data.domain.Page;

import com.wd.cloud.docdelivery.domain.HelpRecord;
import org.springframework.data.domain.Pageable;

/**
 * @author He Zhigang
 * @date 2018/5/8
 * @Description:
 */
public interface BackendService {

    /**
     * 获取互助列表
     * @return
     */
    Page<HelpRecord> getHelpList(Pageable pageable,Map<String,Object> param);

    /**
     * 获取单条互助记录
     * @param id
     * @return
     */
    HelpRecord get(Long id);
    
    /**
     * 查询待审核的
     * @param giveRecordId
     * @return
     */
    GiveRecord getWaitAudit(Long giveRecordId);

    /**
     * 更新互助记录
     * @param helpRecord
     */
    void updateHelRecord(HelpRecord helpRecord);
    
}
