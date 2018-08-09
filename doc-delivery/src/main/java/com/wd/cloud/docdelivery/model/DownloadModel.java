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
    private InputStream inputStream;
    /**
     * 下载文件名，以文献标题对MD5文件进行重命名
     */
    private String downloadFileName;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }
}
