package com.wd.cloud.docdelivery.model;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author He Zhigang
 * @date 2018/5/16
 * @Description:
 */
public class HelpModel {

    /**
     * 求助用户ID
     */
    private Long helpUserId;

    /**
     * 后台操作用户ID
     */
    private Long processUserId;

    /**
     * 求助渠道
     */
    @NotNull
    private Integer helpChannel;

    /**
     * 求助用户所属机构
     */
    private Long helpUserScid;

    /**
     * 求助文件标题
     */
    @NotNull
    private String docTitle;

    /**
     * 求助文献连接
     */
    @URL
    private String docHref;

    /**
     * 求助用户邮箱
     */
    @Email
    @NotNull
    private String helpEmail;

    public Long getHelpUserId() {
        return helpUserId;
    }

    public void setHelpUserId(Long helpUserId) {
        this.helpUserId = helpUserId;
    }

    public Long getProcessUserId() {
        return processUserId;
    }

    public void setProcessUserId(Long processUserId) {
        this.processUserId = processUserId;
    }

    public Integer getHelpChannel() {
        return helpChannel;
    }

    public void setHelpChannel(Integer helpChannel) {
        this.helpChannel = helpChannel;
    }

    public Long getHelpUserScid() {
        return helpUserScid;
    }

    public void setHelpUserScid(Long helpUserScid) {
        this.helpUserScid = helpUserScid;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocHref() {
        return docHref;
    }

    public void setDocHref(String docHref) {
        this.docHref = docHref;
    }

    public String getHelpEmail() {
        return helpEmail;
    }

    public void setHelpEmail(String helpEmail) {
        this.helpEmail = helpEmail;
    }
}
