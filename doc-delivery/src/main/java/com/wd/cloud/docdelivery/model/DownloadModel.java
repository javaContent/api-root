package com.wd.cloud.docdelivery.model;

import java.io.File;
import java.io.InputStream;

/**
 * @author He Zhigang
 * @date 2018/5/24
 * @Description: 文件下载对象
 */
public class DownloadModel {
    /**
     * 下载的真实文件对象
     */
    private byte[] fileByte;
    private File file;
    /**
     * 下载文件名，以文献标题对MD5文件进行重命名
     */
    private String downloadFileName;

    public byte[] getFileByte() {
        return fileByte;
    }

    public DownloadModel setFileByte(byte[] fileByte) {
        this.fileByte = fileByte;
        return this;
    }

    public File getFile() {
        return file;
    }

    public DownloadModel setFile(File file) {
        this.file = file;
        return this;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }
}
