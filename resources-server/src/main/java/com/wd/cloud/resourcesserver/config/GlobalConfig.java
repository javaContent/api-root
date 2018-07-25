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

    private String resourcePath;


    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

}
