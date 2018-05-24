package com.wd.cloud.docdelivery.model;

import java.io.File;

/**
 * @author He Zhigang
 * @date 2018/5/24
 * @Description:
 */
public class DownloadModel {
    private File docFile;
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
