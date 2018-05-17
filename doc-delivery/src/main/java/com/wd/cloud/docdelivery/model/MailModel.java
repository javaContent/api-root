package com.wd.cloud.docdelivery.model;

import cn.hutool.extra.mail.MailAccount;

/**
 * @author He Zhigang
 * @date 2018/5/17
 * @Description:
 */
public class MailModel {

    private MailAccount account;

    /**
     * 有效期毫秒数
     */
    private long exp = 1000 * 60 * 60 * 24 * 15;

    /**
     * 提叫第三方处理
     */
    private String outherSubject;
    private String outherCountent;
    /**
     * 应助成功标题
     */
    private String successSubject;
    private String successContent;
    /**
     * 无有效应助，应助失败
     */
    private String failSubject;
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

    public String getOutherCountent() {
        return outherCountent;
    }

    public void setOutherCountent(String outherCountent) {
        this.outherCountent = outherCountent;
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
