package com.wd.cloud.resourcesserver.model;

import java.io.File;

/**
 * @author He Zhigang
 * @date 2018/8/22
 * @Description:
 */
public class FileObjModel {
    private String fileName;
    private byte[] fileByte;
    private File file;

    public String getFileName() {
        return fileName;
    }

    public FileObjModel setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public byte[] getFileByte() {
        return fileByte;
    }

    public FileObjModel setFileByte(byte[] fileByte) {
        this.fileByte = fileByte;
        return this;
    }

    public File getFile() {
        return file;
    }

    public FileObjModel setFile(File file) {
        this.file = file;
        return this;
    }
}
