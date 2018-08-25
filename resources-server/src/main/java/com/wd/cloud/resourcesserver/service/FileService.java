package com.wd.cloud.resourcesserver.service;

import com.wd.cloud.resourcesserver.model.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author He Zhigang
 * @date 2018/7/20
 * @Description:
 */
public interface FileService {

    /**
     * 保存文件至磁盘目录
     * @param savePath 目录名称
     * @param fileName 文件名称
     * @param file 文件
     * @return
     * @throws IOException
     */
    boolean saveToDisk(String savePath, String fileName, MultipartFile file) throws IOException;

    /**
     * 保存文件至hbase
     * @param tableName
     * @param fileName
     * @param file
     * @return
     * @throws IOException
     */
    boolean saveToHbase(String tableName, String fileName, MultipartFile file) throws IOException;

    /**
     * 从hbase获取文件
     * @param rowKey
     * @param tableName
     * @return
     */
    FileModel getFileToHbase(String rowKey, String tableName);
}
