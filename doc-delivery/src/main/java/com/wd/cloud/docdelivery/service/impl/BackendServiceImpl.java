package com.wd.cloud.docdelivery.service.impl;

import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.repository.HelpRecordRepository;
import com.wd.cloud.docdelivery.service.BackendService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/8
 * @Description:
 */
public class BackendServiceImpl implements BackendService {

    HelpRecordRepository helpRecordRepository;

    @Override
    public Page getHelpList(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize, null);
        return helpRecordRepository.findAll(pageable);
    }
    
    @Override
    public HelpRecord get(Long id) {
    	return helpRecordRepository.findById(id);
    }
    
    @Override
    public HelpRecord getAudit(Long id) {
    	return helpRecordRepository.findByIdAndStatus(id,2);
    }
    
    @Override
    public void update(HelpRecord helpRecord) {
    	helpRecordRepository.update(helpRecord);
    }
    
    
}
