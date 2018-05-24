package com.wd.cloud.docdelivery.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wd.cloud.docdelivery.domain.GiveRecord;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.enums.AuditEnum;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.model.Md5FileModel;
import com.wd.cloud.docdelivery.repository.GiveRecordRepository;
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
public class FrontServiceImpl implements FrontService {

    @Autowired
    LiteratureRepository literatureRepository;

    @Autowired
    HelpRecordRepository helpRecordRepository;

    @Autowired
    GiveRecordRepository giveRecordRepository;

    @Override
    public Literature queryLiterature(Literature literature) {
        Literature literatureData;
        if (StrUtil.isNotEmpty(literature.getDocHref())) {
            literatureData = literatureRepository.findByDocTitleAndDocHref(literature.getDocTitle(), literature.getDocHref());
        } else {
            literatureData = literatureRepository.findByDocTitle(literature.getDocTitle());
        }
        return literatureData;
    }


    @Override
    public Literature saveLiterature(Literature literature) {
        return literatureRepository.save(literature);
    }

    @Override
    public GiveRecord saveGiveRecord(GiveRecord giveRecord) {
        return giveRecordRepository.save(giveRecord);
    }

    @Override
    public void saveFilename(Long helpRecordId, Long giveUserId, Md5FileModel md5FileModel, String giveIp) {
        HelpRecord helpRecord = helpRecordRepository.getOne(helpRecordId);
        GiveRecord giveRecord = new GiveRecord();
        giveRecord.setDocFileName(md5FileModel.getName());
        giveRecord.setDocFileType(md5FileModel.getType());
        giveRecord.setGiverId(giveUserId);
        giveRecord.setGiverIp(giveIp);
        giveRecord.setHelpRecord(helpRecord);
        //前台上传的，需要后台人员再审核
        giveRecord.setAuditStatus(AuditEnum.WAIT.getCode());
        //更新求助状态为待审核
        helpRecord.setStatus(HelpStatusEnum.WAIT_AUDIT.getCode());
        giveRecordRepository.save(giveRecord);
        //会关联更新文献元数据的docFilename字段
        helpRecordRepository.save(helpRecord);
    }

    @Override
    public HelpRecord saveHelpRecord(HelpRecord helpRecord) {
        return helpRecordRepository.save(helpRecord);
    }

    @Override
    public Page<HelpRecord> getHelpRecordsForUser(Integer helperId, Pageable pageable) {
        return helpRecordRepository.findByHelperId(helperId, pageable);
    }

    @Override
    public Page<HelpRecord> getHelpRecordsForEmail(String helperEmail, Pageable pageable) {
        return helpRecordRepository.findByHelperEmail(helperEmail, pageable);
    }

    @Override
    public Page<HelpRecord> getWaitHelpRecords(Pageable pageable) {
//        Sort sort = new Sort(Direction.DESC, "gmtCreate");
//        Pageable pageable = PageRequest.of(pageNum-1, pageSize,sort);
        Page<HelpRecord> waitHelpRecords = helpRecordRepository.findByStatus(HelpStatusEnum.WAIT_AUDIT.getCode(), pageable);
        return waitHelpRecords;
    }

    @Override
    public Page<HelpRecord> getAllHelpRecord(Pageable pageable) {
        return helpRecordRepository.findAll(pageable);
    }

}
