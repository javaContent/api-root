package com.wd.cloud.docdelivery.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.repository.HelpRecordRepository;
import com.wd.cloud.docdelivery.repository.LiteratureRepository;
import com.wd.cloud.docdelivery.service.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description:
 */
@Service("frontService")
public class FrontServiceImpl implements FrontService{

    @Autowired
    LiteratureRepository literatureRepository;

    @Autowired
    HelpRecordRepository helpRecordRepository;

    @Override
    public Literature queryLiterature(Literature literature){
        Literature literatureData;
        if (StrUtil.isNotEmpty(literature.getDocHref())){
            literatureData = literatureRepository.findByDocTitleAndDocHref(literature.getDocTitle(),literature.getDocHref());
        }else{
            literatureData = literatureRepository.findByDocTitle(literature.getDocTitle());
        }
        return literatureData;
    }


    @Override
    public Literature saveLiterature(Literature literature) {
        return literatureRepository.save(literature);
    }

    @Override
    public HelpRecord saveHelpRecord(HelpRecord helpRecord) {
        return helpRecordRepository.save(helpRecord);
    }

    @Override
    public Page<HelpRecord> getHelpRecordsForUser(Integer helpUserId, Pageable pageable) {
        return helpRecordRepository.findByHelpUserId(helpUserId, pageable);
    }

    @Override
    public Page<HelpRecord> getHelpRecordsForEmail(String helpEmail, Pageable pageable) {
        return helpRecordRepository.findByHelpEmail(helpEmail,pageable);
    }

    @Override
    public Page<HelpRecord> getWaitHelpRecords(Pageable pageable) {
//        Sort sort = new Sort(Direction.DESC, "gmtCreate");
//        Pageable pageable = PageRequest.of(pageNum-1, pageSize,sort);
        Page<HelpRecord> waitHelpRecords = helpRecordRepository.findByStatus(HelpStatusEnum.WAITHELP.getCode(),pageable);
        return waitHelpRecords;
    }

    @Override
    public Page<HelpRecord> getAllHelpRecord(Pageable pageable) {
        return helpRecordRepository.findAll(pageable);
    }

}
