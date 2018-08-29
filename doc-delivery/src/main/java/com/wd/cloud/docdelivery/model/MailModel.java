package com.wd.cloud.docdelivery.model;

import cn.hutool.extra.mail.Mail;

/**
 * @author He Zhigang
 * @date 2018/5/17
 * @Description:
 */
public class MailModel extends Mail{

    /**
     * 有效期毫秒数
     */
    private long exp = 1000 * 60 * 60 * 24 * 15;

    private MailContentTemplate notify;
    /**
     * 提交第三方处理
     */
    private MailContentTemplate outher;

    /**
     * 应助成功标题
     */
    private MailContentTemplate success;

    /**
     * 无有效应助，应助失败
     */
    private MailContentTemplate failed;

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public MailContentTemplate getNotify() {
        return notify;
    }

    public void setNotify(MailContentTemplate notify) {
        this.notify = notify;
    }

    public MailContentTemplate getOuther() {
        return outher;
    }

    public void setOuther(MailContentTemplate outher) {
        this.outher = outher;
    }

    public MailContentTemplate getSuccess() {
        return success;
    }

    public void setSuccess(MailContentTemplate success) {
        this.success = success;
    }

    public MailContentTemplate getFailed() {
        return failed;
    }

    public void setFailed(MailContentTemplate failed) {
        this.failed = failed;
    }

}
