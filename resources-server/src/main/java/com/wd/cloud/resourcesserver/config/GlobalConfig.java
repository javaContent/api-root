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

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }
}
