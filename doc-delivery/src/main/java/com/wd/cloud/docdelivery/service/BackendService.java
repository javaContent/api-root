package com.wd.cloud.docdelivery.service;

import org.springframework.data.domain.Page;

import com.wd.cloud.docdelivery.domain.HelpRecord;

/**
 * @author He Zhigang
 * @date 2018/5/8
 * @Description:
 */
public interface BackendService {

    Page getHelpList(int pageNum, int pageSize);
    
    HelpRecord get(Long id);
    
    /**
     * 查询需要审核的
     * @param id
     * @return
     */
    HelpRecord getAudit(Long id);
    
    void update(HelpRecord helpRecord);
    
}
