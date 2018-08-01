package com.wd.cloud.docdelivery.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.druid.util.HttpClientUtils;
import com.wd.cloud.docdelivery.config.GlobalConfig;
import com.wd.cloud.docdelivery.entity.DocFile;
import com.wd.cloud.docdelivery.entity.GiveRecord;
import com.wd.cloud.docdelivery.entity.HelpRecord;
import com.wd.cloud.docdelivery.entity.Literature;
import com.wd.cloud.docdelivery.model.DownloadModel;
import com.wd.cloud.docdelivery.repository.DocFileRepository;
import com.wd.cloud.docdelivery.repository.GiveRecordRepository;
import com.wd.cloud.docdelivery.repository.HelpRecordRepository;
import com.wd.cloud.docdelivery.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

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

    @Override
    public DownloadModel getDownloadFile(Long helpRecordId) {
        HelpRecord helpRecord = helpRecordRepository.getOne(helpRecordId);
        GiveRecord giveRecord = giveRecordRepository.findByHelpRecord(helpRecord);
        String fileName = giveRecord.getDocFile().getFileName();
        String docTitle = helpRecord.getLiterature().getDocTitle();
        //以文献标题作为文件名，标题中可能存在不符合系统文件命名规范，在这里规范一下。
        docTitle = FileUtil.cleanInvalid(docTitle);
        DownloadModel downloadModel = new DownloadModel();
        InputStream inputStream = HttpRequest.get(getResourceUrl(fileName)).execute().bodyStream();

        downloadModel.setInputStream(inputStream);
        String ext = StrUtil.subAfter(fileName,".",true);
        String downLoadFileName = docTitle + "." + ext;
        downloadModel.setDownloadFileName(downLoadFileName);
        return downloadModel;
    }


    @Override
    public String getDownloadUrl(Long helpRecordId) {
        return globalConfig.getCloudDomain() + "/doc-delivery/file/download/" + helpRecordId;
    }

    private String getResourceUrl(String fileName) {
        return globalConfig.getCloudDomain()+ "/resources-server/" + fileName;
    }

}
