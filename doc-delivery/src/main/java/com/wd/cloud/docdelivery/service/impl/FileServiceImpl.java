package com.wd.cloud.docdelivery.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

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
    public DocFile saveFile(Literature literature, MultipartFile file) throws IOException {

        //文件MD5值
        String md5File = DigestUtil.md5Hex(file.getInputStream());
        //文件后缀
        String extName = StrUtil.subAfter(file.getOriginalFilename(), ".", true);
        DocFile docFile = docFileRepository.findByLiteratureAndFileNameAndFileType(literature, md5File, extName);
        if (docFile == null) {
            docFile = new DocFile();
            docFile.setFileName(md5File);
            docFile.setFileType(extName);
            docFile.setLiterature(literature);
            docFile = docFileRepository.save(docFile);
        }
        //文件如果不存在，则保存，否则直接返回文件的MD5名
        if (!FileUtil.exist(new File(globalConfig.getSavePath(), md5File))) {
            //创建一个新文件
            File attachFile = FileUtil.touch(globalConfig.getSavePath(), md5File);
            //将文件流写入文件中
            FileUtil.writeFromStream(file.getInputStream(), attachFile);
        }

        return docFile;
    }

    @Override
    public DownloadModel getDownloadFile(Long helpRecordId) {
        HelpRecord helpRecord = helpRecordRepository.getOne(helpRecordId);
        GiveRecord giveRecord = giveRecordRepository.findByHelpRecord(helpRecord);
        String fileName = giveRecord.getDocFile().getFileName();
        String fileType = giveRecord.getDocFile().getFileType();
        String docTitle = helpRecord.getLiterature().getDocTitle();
        //以文献标题作为文件名，标题中可能存在不符合系统文件命名规范，在这里规范一下。
        docTitle = FileUtil.cleanInvalid(docTitle);
        DownloadModel downloadModel = new DownloadModel();

        File downloadFile = new File(globalConfig.getSavePath(), fileName);

        downloadModel.setDocFile(downloadFile);
        String downLoadFileName = docTitle + "." + fileType;
        downloadModel.setDownloadFileName(downLoadFileName);
        return downloadModel;
    }


    @Override
    public String getDownloadUrl(Long helpRecordId) {
        return globalConfig.getBaseUrl() + "/file/download/" + helpRecordId;
    }

}
