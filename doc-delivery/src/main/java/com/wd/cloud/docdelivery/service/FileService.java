package com.wd.cloud.docdelivery.service;

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
     * @param file
     * @return
     * @throws IOException
     */
    String saveFile(MultipartFile file) throws IOException;
}
