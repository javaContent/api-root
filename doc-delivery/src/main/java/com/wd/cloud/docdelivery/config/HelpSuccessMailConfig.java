package com.wd.cloud.docdelivery.config;

import cn.hutool.extra.mail.MailAccount;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author He Zhigang
 * @date 2018/5/3
 */
@Component
@ConfigurationProperties(value = "mail.success")
public class HelpSuccessMailConfig {

    private String subject;
    private String content;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}