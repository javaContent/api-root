package com.wd.cloud.docdelivery.model;

import cn.hutool.extra.mail.MailAccount;

/**
 * @author He Zhigang
 * @date 2018/5/17
 * @Description:
 */
public class MailModel {

    /**
     * 发送邮件账号对象
     */
    private MailAccount account;

    /**
     * 有效期毫秒数
     */
    private long exp = 1000 * 60 * 60 * 24 * 15;

    /**
     * 提交第三方处理
     */
    private String outherSubject;
    /**
     * 提交第三方处理邮件内容
     */
    private String outherContent;
    /**
     * 应助成功标题
     */
    private String successSubject;
    /**
     * 应助成功邮件内容
     */
    private String successContent;
    /**
     * 无有效应助，应助失败
     */
    private String failSubject;
    /**
     * 无有效应助，应助失败邮件内容
     */
    private String failContent;


    public MailAccount getAccount() {
        return account;
    }

    public void setAccount(MailAccount account) {
        this.account = account;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public String getOutherSubject() {
        return outherSubject;
    }

    public void setOutherSubject(String outherSubject) {
        this.outherSubject = outherSubject;
    }

    public String getOutherContent() {
        return outherContent;
    }

    public void setOutherContent(String outherContent) {
        this.outherContent = outherContent;
    }

    public String getSuccessSubject() {
        return successSubject;
    }

    public void setSuccessSubject(String successSubject) {
        this.successSubject = successSubject;
    }

    public String getSuccessContent() {
        return successContent;
    }

    public void setSuccessContent(String successContent) {
        this.successContent = successContent;
    }

    public String getFailSubject() {
        return failSubject;
    }

    public void setFailSubject(String failSubject) {
        this.failSubject = failSubject;
    }

    public String getFailContent() {
        return failContent;
    }

    public void setFailContent(String failContent) {
        this.failContent = failContent;
    }


}
