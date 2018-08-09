package com.wd.cloud.docdelivery.service;

import com.wd.cloud.docdelivery.entity.DocFile;
import com.wd.cloud.docdelivery.entity.Literature;
import com.wd.cloud.docdelivery.model.DownloadModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author He Zhigang
 * @date 2018/5/16
 * @Description:
 */
public interface FileService {

    DownloadModel getDownloadFile(Long helpRecordId);

    DownloadModel getWaitAuditFile(Long helpRecordId);

    String getDownloadUrl(Long helpRecordId);
}
