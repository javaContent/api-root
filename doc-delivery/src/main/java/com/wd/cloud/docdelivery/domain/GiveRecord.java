package com.wd.cloud.docdelivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author He Zhigang
 * @date 2018/5/22
 * @Description: 应助记录
 */
@Entity
@Table(name = "give_record")
public class GiveRecord extends AbstractDBModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 一个求助可能有多个应助，但只有一个应助有效，失败的应助作为应助记录存在
     */
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private HelpRecord helpRecord;

    /**
     * 文件ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private DocFile docFile;
    /**
     * 应助者ID
     */
    private Long giverId;

    /**
     * 应助者名称
     */
    private String giverName;

    /**
     * 应助者IP
     */
    private String giverIp;

    /**
     * 应助者类型：
     * 0：系统自动应助，
     * 1：管理员应助
     * 2：用户应助，
     * 3：第三方应助,
     * 4:其它
     */
    private Integer giverType;

    /**
     * 0：待审核，1：审核通过，2：审核不通过，4：待上传
     */
    private Integer auditStatus;

    /**
     * 审核人
     */
    private Long auditorId;

    /**
     * 审核人名称
     */
    private String auditorName;

    /**
     * 审核失败原因
     */
    @ManyToOne
    private AuditMsg auditMsg;

    /**
     * 是否可复用
     */
    @Column(name = "is_enable", columnDefinition = "tinyint default 1")
    private boolean enable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public HelpRecord getHelpRecord() {
        return helpRecord;
    }

    public void setHelpRecord(HelpRecord helpRecord) {
        this.helpRecord = helpRecord;
    }

    public Long getGiverId() {
        return giverId;
    }

    public void setGiverId(Long giverId) {
        this.giverId = giverId;
    }

    public String getGiverIp() {
        return giverIp;
    }

    public void setGiverIp(String giverIp) {
        this.giverIp = giverIp;
    }

    public Integer getGiverType() {
        return giverType;
    }

    public void setGiverType(Integer giverType) {
        this.giverType = giverType;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public AuditMsg getAuditMsg() {
        return auditMsg;
    }

    public void setAuditMsg(AuditMsg auditMsg) {
        this.auditMsg = auditMsg;
    }

    public String getGiverName() {
        return giverName;
    }

    public void setGiverName(String giverName) {
        this.giverName = giverName;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public DocFile getDocFile() {
        return docFile;
    }

    public void setDocFile(DocFile docFile) {
        this.docFile = docFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        GiveRecord that = (GiveRecord) o;
        return enable == that.enable &&
                Objects.equals(id, that.id) &&
                Objects.equals(helpRecord, that.helpRecord) &&
                Objects.equals(docFile, that.docFile) &&
                Objects.equals(giverId, that.giverId) &&
                Objects.equals(giverName, that.giverName) &&
                Objects.equals(giverIp, that.giverIp) &&
                Objects.equals(giverType, that.giverType) &&
                Objects.equals(auditStatus, that.auditStatus) &&
                Objects.equals(auditorId, that.auditorId) &&
                Objects.equals(auditorName, that.auditorName) &&
                Objects.equals(auditMsg, that.auditMsg);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, helpRecord, docFile, giverId, giverName, giverIp, giverType, auditStatus, auditorId, auditorName, auditMsg, enable);
    }

    @Override
    public String toString() {
        return "GiveRecord{" +
                "id=" + id +
                ", helpRecord=" + helpRecord +
                ", docFile=" + docFile +
                ", giverId=" + giverId +
                ", giverName='" + giverName + '\'' +
                ", giverIp='" + giverIp + '\'' +
                ", giverType=" + giverType +
                ", auditStatus=" + auditStatus +
                ", auditorId=" + auditorId +
                ", auditorName='" + auditorName + '\'' +
                ", auditMsg=" + auditMsg +
                ", enable=" + enable +
                '}';
    }
}
