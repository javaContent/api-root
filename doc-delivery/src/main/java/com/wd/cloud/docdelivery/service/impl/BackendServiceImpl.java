package com.wd.cloud.docdelivery.service.impl;

import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.repository.HelpRecordRepository;
import com.wd.cloud.docdelivery.service.BackendService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * @author He Zhigang
 * @date 2018/5/8
 * @Description:
 */
@Service("backendService")
public class BackendServiceImpl implements BackendService {

    HelpRecordRepository helpRecordRepository;

    @Override
    public Page getHelpList(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize, null);
        return helpRecordRepository.findAll(pageable);
    }
    
    @Override
    public HelpRecord get(Long id) {
    	return helpRecordRepository.getOne(id);
    }
    
    @Override
    public HelpRecord getWaitAudit(Long id) {
    	return helpRecordRepository.findByIdAndStatus(id, HelpStatusEnum.WAITAUDIT.getCode());
    }
    
    @Override
    public void updateHelRecord(HelpRecord helpRecord) {
    	helpRecordRepository.save(helpRecord);
    }
    
    
}
