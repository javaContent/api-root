package com.wd.cloud.docdelivery.config;

import com.wd.cloud.docdelivery.model.MailModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@Component
@ConfigurationProperties(value = "help.mail")
public class HelpMailConfig {
    /**
     * 默认配置
     */
    private MailModel pub;
    /**
     * spis配置
     */
    private MailModel spis;
    /**
     * crs配置
     */
    private MailModel crs;

    /**
     * 智汇云配置
     */
    private MailModel zhy;

    public MailModel getPub() {
        return pub;
    }

    public void setPub(MailModel pub) {
        this.pub = pub;
    }

    public MailModel getSpis() {
        return spis;
    }

    public void setSpis(MailModel spis) {
        this.spis = spis;
    }

    public MailModel getCrs() {
        return crs;
    }

    public void setCrs(MailModel crs) {
        this.crs = crs;
    }

    public MailModel getZhy() {
        return zhy;
    }

    public void setZhy(MailModel zhy) {
        this.zhy = zhy;
    }
}