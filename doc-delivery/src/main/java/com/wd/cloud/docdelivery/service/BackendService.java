package com.wd.cloud.docdelivery.service;

import org.springframework.data.domain.Page;

import com.wd.cloud.docdelivery.domain.HelpRecord;

/**
 * @author He Zhigang
 * @date 2018/5/8
 * @Description:
 */
public interface BackendService {

    /**
     * 获取互助列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page getHelpList(int pageNum, int pageSize);

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
