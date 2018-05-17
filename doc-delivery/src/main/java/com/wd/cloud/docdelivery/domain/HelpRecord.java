package com.wd.cloud.docdelivery.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description: 互助记录
 */
@Entity
@Table(name="help_record")
public class HelpRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 文献ID
     */
    @Column(name = "literature_id")
    private Long literatureId;

    /**
     * 求助的email地址
     */
    @Email
    @Column(name = "help_email")
    private String helpEmail;

    /**
     * 求助用户，未登录用户为null
     */
    @Column(name = "help_user_id")
    private Long helpUserId;

    /**
     * 求助用户的学校id
     */
    @Column(name = "help_user_scid")
    private Long helpUserScid;
    /**
     * 求助IP
     */
    @Column(name = "help_ip")
    private String helpIp;

    /**
     * 求助渠道，1：QQ，2：SPIS，3：CRS，4：ZHY
     */
    @Column(name = "help_channel")
    private Integer helpChannel;

    /**
     * 应助用户,未登录用户为null
     */
    @Column(name = "give_user_id")
    private Long giveUserId;
    /**
     * 应助IP
     */
    @Column(name = "give_ip")
    private String giveIp;

    /**
     * 互助状态 0：待应助，0：审核不通过,1:为应助中,2: 已应助待审核，3：已审核通过，
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 处理类型
     * 0：表示未处理
     * 1：表示已经处理并邮件回复
     * 2：表示提交第三方处理
     * 3：没有结果
     * 4:表示其他途径
     * 5：没结果（图书）
     */
    @Column(name = "process_type")
    private Integer processType;

    /**
     * 负责处理的用户的ID
     * 审核人，如果是用户应助，则有对应的审核人
     */
    @Column(name = "process_User_id")
    private Long processUserId;

    /**
     * 文献标题
     */
    @Column(name = "doc_title")
    private String docTitle;

    /**
     * 文献地址
     */
    @Column(name = "doc_href")
    private String docHref;

    /**
     * 文献全文的文件名称
     */
    @Column(name = "doc_filename")
    private String docFilename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getLiteratureId() {
        return literatureId;
    }

    public void setLiteratureId(Long literatureId) {
        this.literatureId = literatureId;
    }

    public String getHelpEmail() {
        return helpEmail;
    }

    public void setHelpEmail(String helpEmail) {
        this.helpEmail = helpEmail;
    }

    public String getHelpIp() {
        return helpIp;
    }

    public void setHelpIp(String helpIp) {
        this.helpIp = helpIp;
    }

    public String getGiveIp() {
        return giveIp;
    }

    public void setGiveIp(String giveIp) {
        this.giveIp = giveIp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getDocFilename() {
        return docFilename;
    }

    public void setDocFilename(String docFilename) {
        this.docFilename = docFilename;
    }

    public Long getHelpUserId() {
        return helpUserId;
    }

    public void setHelpUserId(Long helpUserId) {
        this.helpUserId = helpUserId;
    }

    public Long getHelpUserScid() {
        return helpUserScid;
    }

    public void setHelpUserScid(Long helpUserScid) {
        this.helpUserScid = helpUserScid;
    }

    public Long getGiveUserId() {
        return giveUserId;
    }

    public void setGiveUserId(Long giveUserId) {
        this.giveUserId = giveUserId;
    }

    public Integer getProcessType() {
        return processType;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
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

    @Override
    public String toString() {
        return "HelpRecord{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", literatureId=" + literatureId +
                ", helpEmail='" + helpEmail + '\'' +
                ", helpUserId=" + helpUserId +
                ", helpUserScid=" + helpUserScid +
                ", helpIp='" + helpIp + '\'' +
                ", helpChannel=" + helpChannel +
                ", giveUserId=" + giveUserId +
                ", giveIp='" + giveIp + '\'' +
                ", status=" + status +
                ", processType=" + processType +
                ", processUserId=" + processUserId +
                ", docTitle='" + docTitle + '\'' +
                ", docHref='" + docHref + '\'' +
                ", docFilename='" + docFilename + '\'' +
                '}';
    }
}
