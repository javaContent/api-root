package com.wd.cloud.docdelivery.service;

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
    Page<HelpRecord> getHelpList(Pageable pageable);

    /**
     * 获取单条互助记录
     * @param id
     * @return
     */
    HelpRecord get(Long id);
    
    /**
     * 查询待审核的
     * @param id
     * @return
     */
    HelpRecord getWaitAudit(Long id);

    /**
     * 更新互助记录
     * @param helpRecord
     */
    void updateHelRecord(HelpRecord helpRecord);
    
}
