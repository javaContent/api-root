package com.wd.cloud.commons.config;

import java.io.Serializable;

/**
 * @author DimonHo
 * @date 2017/11/2
 * @since
 */
public class MailProperties implements Serializable {
    private boolean enable = true;
    //有效期，十分钟有效
    private long effective;
    //邮件内容，例如：你的验证码为%s
    private String content;

    private String subject;
    //来自哪个用户发送
    private String from;

    public long getEffective() {
        return effective;
    }

    public void setEffective(long effective) {
        this.effective = effective;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
