package com.wd.cloud.resourcesserver.model;

/**
 * @author He Zhigang
 * @date 2018/8/22
 * @Description:
 */
public class FileModel {
    private String rowKey;
    private byte[] file;

    public String getRowKey() {
        return rowKey;
    }

    public FileModel setRowKey(String rowKey) {
        this.rowKey = rowKey;
        return this;
    }

    public byte[] getFile() {
        return file;
    }

    public FileModel setFile(byte[] file) {
        this.file = file;
        return this;
    }
}
