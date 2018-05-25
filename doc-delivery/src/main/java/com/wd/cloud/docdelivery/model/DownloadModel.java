package com.wd.cloud.docdelivery.model;

import java.io.File;

/**
 * @author He Zhigang
 * @date 2018/5/24
 * @Description: 文件下载对象
 */
public class DownloadModel {
    /**
     * 下载的真实文件对象
     */
    private File docFile;
    /**
     * 下载文件名，以文献标题对MD5文件进行重命名
     */
    private String downloadFileName;

    public File getDocFile() {
        return docFile;
    }

    public void setDocFile(File docFile) {
        this.docFile = docFile;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }
}
