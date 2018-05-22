package com.wd.cloud.docdelivery.service.impl;

import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.repository.HelpRecordRepository;
import com.wd.cloud.docdelivery.service.BackendService;

import cn.hutool.core.date.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author He Zhigang
 * @date 2018/5/8
 * @Description:
 */
@Service("backendService")
public class BackendServiceImpl implements BackendService {

    @Autowired
    HelpRecordRepository helpRecordRepository;

    @Override
    public Page getHelpList(Pageable pageable,Map<String,Object> param) {
    	Short helpUserScid = (Short) param.get("scid");
        Short processType = (Short) param.get("processType");
        String keyword = (String) param.get("keyword");
        String beginTime =  (String) param.get("beginTime");
        String endTime = (String) param.get("endTime");
        
        Page result =  helpRecordRepository.findAll(new Specification<HelpRecord>()  {
			@Override
			public Predicate toPredicate(Root<HelpRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(helpUserScid != null && helpUserScid != 0) {
					list.add(cb.equal(root.get("helpUserScid").as(Integer.class), helpUserScid));
				}
				if(processType != null && processType != 0) {
					list.add(cb.equal(root.get("processType").as(Integer.class), processType));
				}
				if(!StringUtils.isEmpty(keyword)) {
					list.add(cb.or(cb.like(root.get("literature").get("docTitle").as(String.class), "%" +keyword + "%"), cb.like(root.get("helpEmail").as(String.class), "%" +keyword + "%")));
				}
				if(!StringUtils.isEmpty(beginTime)) {
					list.add(cb.between(root.get("gmtCreate").as(Date.class), DateUtil.parse(beginTime), DateUtil.parse(endTime)));
				}
				
				Predicate[] p = new Predicate[list.size()];
		        return cb.and(list.toArray(p));
			}
        }, pageable);
        return result;
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
