package com.wd.cloud.docdelivery.config;

import cn.hutool.extra.mail.Mail;
import com.wd.cloud.docdelivery.model.MailModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@Component
@ConfigurationProperties(value = "help.mail")
public class HelpMailConfig{

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

    @Bean(name = "spis")
    public MailModel getSpis() {
        return spis;
    }

    public void setSpis(MailModel spis) {
        this.spis = spis;
    }

    @Bean(name = "crs")
    public MailModel getCrs() {
        return crs;
    }

    public void setCrs(MailModel crs) {
        this.crs = crs;
    }

    @Bean(name= "zhy")
    public MailModel getZhy() {
        return zhy;
    }

    public void setZhy(MailModel zhy) {
        this.zhy = zhy;
    }
}