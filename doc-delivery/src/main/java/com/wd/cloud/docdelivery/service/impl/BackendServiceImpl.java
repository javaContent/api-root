package com.wd.cloud.docdelivery.service.impl;

import com.wd.cloud.docdelivery.config.GlobalConfig;
import com.wd.cloud.docdelivery.entity.DocFile;
import com.wd.cloud.docdelivery.entity.GiveRecord;
import com.wd.cloud.docdelivery.entity.HelpRecord;
import com.wd.cloud.docdelivery.entity.Literature;
import com.wd.cloud.docdelivery.enums.AuditEnum;
import com.wd.cloud.docdelivery.model.DownloadModel;
import com.wd.cloud.docdelivery.repository.DocFileRepository;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author He Zhigang
 * @date 2018/5/8
 * @Description:
 */
@Service("backendService")
public class BackendServiceImpl implements BackendService {

    @Autowired
    GlobalConfig globalConfig;

    @Autowired
    HelpRecordRepository helpRecordRepository;

    @Autowired
    GiveRecordRepository giveRecordRepository;

    @Autowired
    LiteratureRepository literatureRepository;

    @Autowired
    DocFileRepository docFileRepository;

    @Override
    public Page<HelpRecord> getHelpList(Pageable pageable, Map<String, Object> param) {
        Short helpUserScid = (Short) param.get("helperScid");
        Short status = (Short) param.get("status");
        String keyword = (String) param.get("keyword");
        String beginTime = (String) param.get("beginTime");
        String endTime = (String) param.get("endTime") + " 23:59:59";
        Page<HelpRecord> result = helpRecordRepository.findAll(new Specification<HelpRecord>() {
            @Override
            public Predicate toPredicate(Root<HelpRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (helpUserScid != null && helpUserScid != 0) {
                    list.add(cb.equal(root.get("helperScid").as(Integer.class), helpUserScid));
                }
                if (status != null && status != 0) {
                	if(status == 1) {//列表查询未处理
                		list.add(cb.or(cb.equal(root.get("status").as(Integer.class), 0),cb.equal(root.get("status").as(Integer.class), 1),cb.equal(root.get("status").as(Integer.class), 2)));
                	} else {
                		list.add(cb.equal(root.get("status").as(Integer.class), status));
                	}
                }
                if (!StringUtils.isEmpty(keyword)) {
                    list.add(cb.or(cb.like(root.get("literature").get("docTitle").as(String.class), "%" + keyword.trim() + "%"), cb.like(root.get("helperEmail").as(String.class), "%" + keyword.trim() + "%")));
                }
                if (!StringUtils.isEmpty(beginTime)) {
                    list.add(cb.between(root.get("gmtCreate").as(Date.class), DateUtil.parse(beginTime), DateUtil.parse(endTime)));
                }

                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, pageable);

        return result;
    }

    @Override
    public Page getLiteratureList(Pageable pageable, Map<String, Object> param) {
		Boolean reusing = (Boolean) param.get("reusing");
		String keyword = (String) param.get("keyword");
		Page<Literature> result = literatureRepository.findAll(new Specification<Literature>() {
			@Override
			public Predicate toPredicate(Root<Literature> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (reusing != null) {
					list.add(cb.equal(root.get("reusing").as(boolean.class), reusing));
				}
				if (!StringUtils.isEmpty(keyword)) {
					list.add(cb.like(root.get("docTitle").as(String.class), "%" + keyword + "%"));
				}
				list.add(cb.isNotEmpty(root.get("docFiles")));
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}
		}, pageable);
		for (Literature literature : result) {
			Set<DocFile> docFiles = literature.getDocFiles();
			List<DocFile> list = new ArrayList<>();
			list.addAll(docFiles);
			if(docFiles != null && docFiles.size() > 1) {
				System.out.println(list.get(0).isReusing());
				System.out.println(list.get(1).isReusing());
				Collections.sort(list, new Comparator<DocFile>() {
				    @Override
			        public int compare(DocFile docFile1, DocFile docFile2) {
			            /**
			             * 升序排的话就是第一个参数.compareTo(第二个参数);
			             * 降序排的话就是第二个参数.compareTo(第一个参数);
			             */
			        	if(!literature.isReusing()) {
			        		 return docFile1.getGmtModified().compareTo(docFile2.getGmtModified());//升序
			        	} else {
			        		if(docFile1.isReusing()) {
			        			return -1;
			        		}
			        		return 1;
			        	}
			        }
			    });
				System.out.println(list.get(0).isReusing());
				System.out.println(list.get(1).isReusing());
			}
			docFiles.clear();
			docFiles.addAll(list);
			literature.setDocFiles(docFiles);
		}
		return result;
    }

    @Override
    public List<DocFile> getDocFileList(Pageable pageable, Long literatureId) {
        Literature literature = new Literature();
        literature.setId(literatureId);
        return docFileRepository.findByLiterature(literature);
    }

    @Override
    public DownloadModel getDowloadFile(long docFileId) {
        DocFile docFile = docFileRepository.getOne(docFileId);
        DownloadModel downloadModel = new DownloadModel();
        String fileName = docFile.getFileName();
        String fileType = docFile.getFileType();
        downloadModel.setDocFile(new File(globalConfig.getSavePath(), fileName));
        downloadModel.setDownloadFileName(fileName + "." + fileType);
        return downloadModel;
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
    public GiveRecord getGiverRecord(HelpRecord helpRecord, int auditStatus, int giverType) {
        return giveRecordRepository.findByHelpRecordAndAuditStatusAndGiverType(helpRecord, auditStatus, giverType);
    }

    @Override
    public void saveGiveRecord(GiveRecord giveRecord) {
        giveRecordRepository.save(giveRecord);
    }

    @Override
    public boolean reusing(Map<String, Object> param) {
        Literature literature = new Literature();
        literature.setId((long) param.get("literatureId"));
        long docFileId = (long) param.get("docFileId");
        boolean reusing = (boolean) param.get("reusing");
        List<DocFile> list = docFileRepository.findByLiterature(literature);
        DocFile doc = null;
        for (DocFile docFile : list) {
            //如果是复用操作，并且已经有文档被复用，则返回false，如果是取消复用，则不会进入
            if (docFile.isReusing() && reusing) {
                return false;
            }
            if (docFile.getId() == docFileId) {
                doc = docFile;
                if (!reusing) {
                    break;
                }
            }
        }
        doc.setReusing(reusing);
        doc.setAuditorId((long) param.get("auditorId"));
        doc.setAuditorName((String) param.get("auditorName"));
        doc.setReMark((String) param.get("reMark"));
        doc.getLiterature().setReusing(reusing);
        if (doc == null) {
            return false;
        }
        docFileRepository.save(doc);
        return true;
    }
    
    
    
    public static void main(String[] args) {
    	List<DocFile> list = new ArrayList<>();
    	DocFile docFile1 = new DocFile();
    	docFile1.setReusing(false);
    	
    	DocFile docFile2 = new DocFile();
    	docFile2.setReusing(true);
    	
    	Set<DocFile> set = new HashSet<DocFile>();
    	
    	
    	set.add(docFile1);
    	set.add(docFile2);
    	
    	list.addAll(set);
		
		Collections.sort(list, new Comparator<DocFile>() {
	        public int compare(DocFile docFile1, DocFile docFile2) {
	            /**
	             * 升序排的话就是第一个参数.compareTo(第二个参数);
	             * 降序排的话就是第二个参数.compareTo(第一个参数);
	             */
	        	
        		if(docFile1.isReusing()) {
        			return -1;
        		}
        		return 1;
	        	
	        }
	    });
		
		System.out.println(list.get(0).isReusing());
		System.out.println(list.get(1).isReusing());
	}

}
