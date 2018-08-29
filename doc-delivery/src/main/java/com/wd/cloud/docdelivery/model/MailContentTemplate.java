package com.wd.cloud.docdelivery.model;

/**
 * @author He Zhigang
 * @date 2018/8/29
 * @Description:
 */
public class MailContentTemplate {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public MailContentTemplate setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MailContentTemplate setContent(String content) {
        this.content = content;
        return this;
    }
}
