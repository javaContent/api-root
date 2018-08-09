package com.wd.cloud.resourcesserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author He Zhigang
 * @date 2018/7/20
 * @Description:
 */
@Component
@ConfigurationProperties(value = "global")
public class GlobalConfig {

    private String resources;

    private String docPath;
    private String imagePath;
    private String journalPath;

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getJournalPath() {
        return journalPath;
    }

    public void setJournalPath(String journalPath) {
        this.journalPath = journalPath;
    }
}
