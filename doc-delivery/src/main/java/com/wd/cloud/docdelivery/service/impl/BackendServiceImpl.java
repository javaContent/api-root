package com.wd.cloud.docdelivery.service.impl;

import com.wd.cloud.docdelivery.domain.DocFile;
import com.wd.cloud.docdelivery.domain.GiveRecord;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.enums.AuditEnum;
import com.wd.cloud.docdelivery.repository.DocFileRepostitory;
import com.wd.cloud.docdelivery.repository.GiveRecordRepository;
import com.wd.cloud.docdelivery.repository.HelpRecordRepository;
import com.wd.cloud.docdelivery.repository.LiteratureRepository;
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

    @Autowired
    GiveRecordRepository giveRecordRepository;
    
    @Autowired
    LiteratureRepository literatureRepository;
    
    @Autowired
    DocFileRepostitory docFileRepostitory;

    @Override
    public Page getHelpList(Pageable pageable, Map<String, Object> param) {
        Short helpUserScid = (Short) param.get("scid");
        Short status = (Short) param.get("status");
        String keyword = (String) param.get("keyword");
        String beginTime = (String) param.get("beginTime");
        String endTime = (String) param.get("endTime") + " 23:59:59";
        Page result = helpRecordRepository.findAll(new Specification<HelpRecord>() {
            @Override
            public Predicate toPredicate(Root<HelpRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (helpUserScid != null && helpUserScid != 0) {
                    list.add(cb.equal(root.get("helperScid").as(Integer.class), helpUserScid));
                }
                if (status != null && status != 0) {
                    list.add(cb.equal(root.get("status").as(Integer.class), status));
                }
                if (!StringUtils.isEmpty(keyword)) {
                    list.add(cb.or(cb.like(root.get("literature").get("docTitle").as(String.class), "%" + keyword + "%"), cb.like(root.get("helpEmail").as(String.class), "%" + keyword + "%")));
                }
                if (!StringUtils.isEmpty(beginTime)) {
                    list.add(cb.between(root.get("gmtCreate").as(Date.class), DateUtil.parse(beginTime), DateUtil.parse(endTime)));
                }

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, pageable);
        
//        List<HelpRecord> list = result.getContent();
//        for (HelpRecord helpRecord : list) {
//        	if(helpRecord.getStatus() == HelpStatusEnum.HELP_SUCCESSED.getCode()) {
//        		GiveRecord giveRecord = giveRecordRepository.findByHelpRecordId(helpRecord);
//        		helpRecord.setGiveRecord(giveRecord);
//        	}
//		}
        return result;
    }
    
    @Override
    public Page getLiteratureList(Pageable pageable, Map<String, Object> param) {
    	Boolean reusing = (Boolean) param.get("reusing");
        String keyword = (String) param.get("keyword");
    	Page result = literatureRepository.findAll(new Specification<Literature>() {
            @Override
            public Predicate toPredicate(Root<Literature> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                
                if (reusing != null) {
                    list.add(cb.equal(root.get("reusing").as(boolean.class), reusing));
                }
                if (!StringUtils.isEmpty(keyword)) {
                    list.add(cb.like(root.get("docTitle").as(String.class), "%" + keyword + "%"));
                }

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, pageable);
		return result;
    }
    
    @Override
	public Page<DocFile> getDocFileList(Pageable pageable, Long literatureId) {
    		return null;
    }
    

    @Override
    public HelpRecord getHelpRecord(Long id) {
        return helpRecordRepository.getOne(id);
    }

    @Override
    public GiveRecord getWaitAudit(Long id) {
        GiveRecord giveRecord = giveRecordRepository.findByIdAndAuditStatus(id, AuditEnum.WAIT.getCode());
        return giveRecord;
    }

    @Override
    public void updateHelRecord(HelpRecord helpRecord) {
        helpRecordRepository.save(helpRecord);
    }
    
    @Override
    public GiveRecord getGiverRecord(HelpRecord helpRecord,int auditStatus,int giverType) {
    	return giveRecordRepository.findByHelpRecordAndAuditStatusAndGiverType(helpRecord,auditStatus,giverType);
    }
    
    @Override
    public void saveGiveRecord(GiveRecord giveRecord) {
    	giveRecordRepository.save(giveRecord);
    }
    
    @Override
    public boolean reusing(Map<String,Object> param) {
    	Literature literature = new Literature();
    	literature.setId((long) param.get("literatureId"));
	long docFileId = (long) param.get("docFileId");
	boolean reusing = (boolean) param.get("reusing");
	List<DocFile> list = docFileRepostitory.findByLiterature(literature);
	DocFile doc = null;
	for (DocFile docFile : list) {
		//如果是复用操作，并且已经有文档被复用，则返回false，如果是取消复用，则不会进入
		if(docFile.isReusing() && reusing) {
			return false;
		}
		if(docFile.getId() == docFileId) {
			doc = docFile;
			break;
		}
	}
	doc.setReusing(reusing);
	doc.setAuditorId((long) param.get("auditorId"));
	doc.setAuditorName((String) param.get("auditorName"));
	doc.setReMark((String) param.get("reMark"));
		if(doc == null) {return false;}
		docFileRepostitory.save(doc);
		return true;
	}

}
