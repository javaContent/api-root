package com.wd.cloud.docdelivery.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.wd.cloud.apifeign.ResourcesServerApi;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.docdelivery.config.GlobalConfig;
import com.wd.cloud.docdelivery.entity.GiveRecord;
import com.wd.cloud.docdelivery.entity.HelpRecord;
import com.wd.cloud.docdelivery.enums.AuditEnum;
import com.wd.cloud.docdelivery.model.DownloadModel;
import com.wd.cloud.docdelivery.repository.DocFileRepository;
import com.wd.cloud.docdelivery.repository.GiveRecordRepository;
import com.wd.cloud.docdelivery.repository.HelpRecordRepository;
import com.wd.cloud.docdelivery.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

/**
 * @author He Zhigang
 * @date 2018/5/16
 * @Description:
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    GlobalConfig globalConfig;

    @Autowired
    DocFileRepository docFileRepository;

    @Autowired
    HelpRecordRepository helpRecordRepository;

    @Autowired
    GiveRecordRepository giveRecordRepository;

    @Autowired
    ResourcesServerApi resourcesServerApi;
    @Override
    public DownloadModel getDownloadFile(Long helpRecordId) {
        HelpRecord helpRecord = helpRecordRepository.getOne(helpRecordId);
        if (checkTimeOut(helpRecord.getGmtModified())){
            return null;
        }
        GiveRecord giveRecord = giveRecordRepository.findByHelpRecord(helpRecord);
        DownloadModel downloadModel = buildDownloadModel(helpRecord, giveRecord);
        return downloadModel;
    }

    @Override
    public DownloadModel getWaitAuditFile(Long helpRecordId) {
        HelpRecord helpRecord = helpRecordRepository.getOne(helpRecordId);
        GiveRecord giveRecord = giveRecordRepository.findByHelpRecordAndAuditStatusEquals(helpRecord, AuditEnum.WAIT.getCode());
        DownloadModel downloadModel = buildDownloadModel(helpRecord, giveRecord);
        return downloadModel;
    }

    private DownloadModel buildDownloadModel(HelpRecord helpRecord, GiveRecord giveRecord) {
        String fileName = giveRecord.getDocFile().getFileName();
        String docTitle = helpRecord.getLiterature().getDocTitle();
        //以文献标题作为文件名，标题中可能存在不符合系统文件命名规范，在这里规范一下。
        docTitle = FileUtil.cleanInvalid(docTitle);
        DownloadModel downloadModel = new DownloadModel();
        ResponseModel<byte[]> fileByte = resourcesServerApi.getFileByteToHf(globalConfig.getHbaseTableName(),fileName);
        downloadModel.setFileByte(fileByte.getBody());
        String ext = StrUtil.subAfter(fileName,".",true);
        String downLoadFileName = docTitle + "." + ext;
        downloadModel.setDownloadFileName(downLoadFileName);
        return downloadModel;
    }


    private boolean checkTimeOut(Date startDate){
       if (15 < DateUtil.betweenDay(startDate, new Date(),true)){
           return true;
       }
       return false;
    }

    @Override
    public String getDownloadUrl(Long helpRecordId) {
        return globalConfig.getCloudDomain() + "/doc-delivery/file/download/" + helpRecordId;
    }
}
