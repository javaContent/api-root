package com.wd.cloud.docdelivery.service;

import com.wd.cloud.docdelivery.domain.DocFile;
import com.wd.cloud.docdelivery.domain.Literature;
import com.wd.cloud.docdelivery.model.Md5FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

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
}
