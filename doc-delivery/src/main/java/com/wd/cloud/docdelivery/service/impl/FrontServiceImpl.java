package com.wd.cloud.docdelivery.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.wd.cloud.docdelivery.config.GlobalConfig;
import com.wd.cloud.docdelivery.domain.DocFile;
import com.wd.cloud.docdelivery.domain.GiveRecord;
import com.wd.cloud.docdelivery.domain.HelpRecord;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.enums.AuditEnum;
import com.wd.cloud.docdelivery.enums.GiveTypeEnum;
import com.wd.cloud.docdelivery.enums.HelpStatusEnum;
import com.wd.cloud.docdelivery.repository.DocFileRepository;
import com.wd.cloud.docdelivery.repository.GiveRecordRepository;
import com.wd.cloud.docdelivery.repository.HelpRecordRepository;
import com.wd.cloud.docdelivery.repository.LiteratureRepository;
import com.wd.cloud.docdelivery.service.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description:
 */
@Service("frontService")
@Transactional(rollbackOn = Exception.class)
public class FrontServiceImpl implements FrontService {

    @Autowired
    GlobalConfig globalConfig;
    @Autowired
    LiteratureRepository literatureRepository;

    @Autowired
    HelpRecordRepository helpRecordRepository;

    @Autowired
    GiveRecordRepository giveRecordRepository;

    @Autowired
    DocFileRepository docFileRepository;

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
    public HelpRecord givingHelp(long helpRecordId, long giverId, String giverName, String giverIp) {
        HelpRecord helpRecord = helpRecordRepository.findByIdAndStatus(helpRecordId, HelpStatusEnum.WAIT_HELP.getCode());
        if (helpRecord != null) {
            helpRecord.setStatus(HelpStatusEnum.HELPING.getCode());
            GiveRecord giveRecord = new GiveRecord();
            giveRecord.setHelpRecord(helpRecord);
            giveRecord.setGiverId(giverId);
            giveRecord.setGiverIp(giverIp);
            giveRecord.setGiverName(giverName);
            giveRecord.setGiverType(GiveTypeEnum.USER.getCode());
            giveRecord.setAuditStatus(AuditEnum.WAIT_UPLOAD.getCode());
            //保存的同时，关联更新求助记录状态
            giveRecordRepository.save(giveRecord);
        }
        return helpRecord;
    }

    @Override
    public boolean cancelGivingHelp(long helpRecordId, long giverId) {
        HelpRecord helpRecord = helpRecordRepository.findByIdAndStatus(helpRecordId, HelpStatusEnum.HELPING.getCode());
        boolean flag = false;
        if (helpRecord != null) {
            giveRecordRepository.deleteByHelpRecordAndAuditStatusAndGiverId(helpRecord, AuditEnum.WAIT_UPLOAD.getCode(), giverId);
            //更新求助记录状态
            helpRecord.setStatus(HelpStatusEnum.WAIT_HELP.getCode());
            helpRecordRepository.save(helpRecord);
            flag = true;
        }
        return flag;
    }

    @Override
    public HelpRecord getHelpingRecord(long helpRecordId) {
        return helpRecordRepository.findByIdAndStatus(helpRecordId, HelpStatusEnum.HELPING.getCode());
    }

    @Override
    public HelpRecord getNotWaitRecord(long helpRecordId) {
        return helpRecordRepository.findByIdAndStatusNot(helpRecordId, HelpStatusEnum.WAIT_HELP.getCode());
    }

    @Override
    public String clearHtml(String docTitle) {
        return HtmlUtil.restoreEscaped(HtmlUtil.cleanHtmlTag(docTitle));
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
    public void createGiveRecord(HelpRecord helpRecord, long giverId, DocFile docFile, String giveIp) {
        //更新求助状态为待审核
        helpRecord.setStatus(HelpStatusEnum.WAIT_AUDIT.getCode());
        GiveRecord giveRecord = giveRecordRepository.findByHelpRecordAndAuditStatusAndGiverId(helpRecord, AuditEnum.WAIT_UPLOAD.getCode(), giverId);
        //关联应助记录
        giveRecord.setDocFile(docFile);
        giveRecord.setGiverId(giverId);
        giveRecord.setGiverIp(giveIp);
        giveRecord.setHelpRecord(helpRecord);
        //前台上传的，需要后台人员再审核
        giveRecord.setAuditStatus(AuditEnum.WAIT.getCode());
        giveRecordRepository.save(giveRecord);

    }

    @Override
    public HelpRecord saveHelpRecord(HelpRecord helpRecord) {
        return helpRecordRepository.save(helpRecord);
    }

    @Override
    public HelpRecord getHelpRecord(long helpRecordId) {
        return helpRecordRepository.getOne(helpRecordId);
    }

    @Override
    public Page<HelpRecord> getHelpRecordsForUser(long helperId, Pageable pageable) {
        return helpRecordRepository.findByHelperId(helperId, pageable);
    }

    @Override
    public Page<HelpRecord> getHelpRecordsForEmail(String helperEmail, Pageable pageable) {
        return helpRecordRepository.findByHelperEmail(helperEmail, pageable);
    }

    @Override
    public Page<HelpRecord> getWaitHelpRecords(int helpChannel, Pageable pageable) {

        int[] status = {HelpStatusEnum.WAIT_HELP.getCode(),
                HelpStatusEnum.HELPING.getCode(),
                HelpStatusEnum.WAIT_AUDIT.getCode(),
                HelpStatusEnum.HELP_THIRD.getCode()};
        Page<HelpRecord> waitHelpRecords = helpRecordRepository.findByHelpChannelAndStatusIn(helpChannel, status, pageable);
        return waitHelpRecords;
    }


    @Override
    public Page<HelpRecord> getFinishHelpRecords(int helpChannel, Pageable pageable) {
        int[] status = {HelpStatusEnum.HELP_SUCCESSED.getCode(), HelpStatusEnum.HELP_FAILED.getCode()};
        Page<HelpRecord> finishHelpRecords = helpRecordRepository.findByHelpChannelAndStatusIn(helpChannel, status, pageable);
        return finishHelpRecords;
    }

    @Override
    public Page<HelpRecord> getAllHelpRecord(Pageable pageable) {
        return helpRecordRepository.findAll(pageable);
    }

    @Override
    public DocFile getReusingFile(Literature literature) {
        DocFile docFiles = docFileRepository.findByLiteratureAndReusingIsTrue(literature);
        return docFiles;
    }

    @Override
    public boolean checkExistsGiveing(long giverId) {
        GiveRecord giveRecord = giveRecordRepository.findByGiverIdAndAuditStatus(giverId, AuditEnum.WAIT_UPLOAD.getCode());
        if (giveRecord != null) {
            return true;
        } else {
            return false;
        }
    }

}
