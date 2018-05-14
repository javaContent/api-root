package com.wd.cloud.docdelivery.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

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
    private Integer literatureId;

    /**
     * 求助的email地址
     */
    private String email;

    /**
     * 求助用户，未登录用户为null
     */
    @Column(name = "help_username")
    private String helpUsername;
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
    @Column(name = "give_username")
    private String giveUsername;
    /**
     * 应助IP
     */
    @Column(name = "give_ip")
    private String giveIp;

    /**
     * 审核人，如果是用户应助，则有对应的审核人
     */
    private String audit;

    /**
     * 互助状态 0：待应助，2: 已应助待审核，1：已审核通过，0：审核不通过
     */
    private Integer status;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getLiteratureId() {
        return literatureId;
    }

    public void setLiteratureId(Integer literatureId) {
        this.literatureId = literatureId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHelpUsername() {
        return helpUsername;
    }

    public void setHelpUsername(String helpUsername) {
        this.helpUsername = helpUsername;
    }

    public String getHelpIp() {
        return helpIp;
    }

    public void setHelpIp(String helpIp) {
        this.helpIp = helpIp;
    }

    public String getGiveUsername() {
        return giveUsername;
    }

    public void setGiveUsername(String giveUsername) {
        this.giveUsername = giveUsername;
    }

    public String getGiveIp() {
        return giveIp;
    }

    public void setGiveIp(String giveIp) {
        this.giveIp = giveIp;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
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

    @Override
    public String toString() {
        return "HelpRecord{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", literatureId=" + literatureId +
                ", email='" + email + '\'' +
                ", helpUsername='" + helpUsername + '\'' +
                ", helpIp='" + helpIp + '\'' +
                ", giveUsername='" + giveUsername + '\'' +
                ", giveIp='" + giveIp + '\'' +
                ", audit='" + audit + '\'' +
                ", status=" + status +
                ", docTitle='" + docTitle + '\'' +
                ", docHref='" + docHref + '\'' +
                ", docFilename='" + docFilename + '\'' +
                '}';
    }
}
