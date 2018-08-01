package com.wd.cloud.resourcesserver.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author He Zhigang
 * @date 2018/7/20
 * @Description:
 */
public interface FileService {

    String save(MultipartFile file, String savePath) throws IOException;

    String save(MultipartFile file, String savePath, String journalId) throws IOException;
}
