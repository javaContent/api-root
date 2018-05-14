package com.wd.cloud.docdelivery.config;

import cn.hutool.extra.mail.MailAccount;
import com.wd.cloud.commons.config.MailProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@Component
@ConfigurationProperties(value = "send")
public class MailConfig extends MailAccount {

    private String host;
    private Integer port;
    private Boolean auth;
    private String user;
    private String pass;

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public MailConfig setHost(String host) {
        this.host = host;
        return this;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public MailConfig setPort(Integer port) {
        this.port = port;
        return this;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public MailConfig setUser(String user) {
        this.user = user;
        return this;
    }

    @Override
    public String getPass() {
        return pass;
    }

    @Override
    public MailConfig setPass(String pass) {
        this.pass = pass;
        return this;
    }
}