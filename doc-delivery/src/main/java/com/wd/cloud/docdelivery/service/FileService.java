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

    /**
     * 保存文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    DocFile saveFile(Literature literature, MultipartFile file) throws IOException;


    DownloadModel getDownloadFile(Long helpRecordId);

    String getDownloadUrl(Long helpRecordId);
}
