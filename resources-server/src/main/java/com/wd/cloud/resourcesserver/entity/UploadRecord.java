package com.wd.cloud.resourcesserver.entity;

import cn.hutool.crypto.SecureUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author He Zhigang
 * @date 2018/9/3
 * @Description:
 */
@Entity
@Table(name = "upload_record",uniqueConstraints = {@UniqueConstraint(columnNames = {"unid"})})
public class UploadRecord extends AbstractEntity {

    @NotNull
    @Column(name = "unid",length = 64)
    private String unid ;
    /**
     * 保存介质：hbase OR 磁盘
     */
    private String target;
    /**
     * 保存路径
     */
    private String path;
    /**
     * 文件名称
     */
    @Column(name = "file_name",length = 1000)
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件是否丢失
     */
    @Column(name = "is_missed",columnDefinition = "tinyint(1) default 0")
    private boolean missed;

    public String getUnid() {
        return unid;
    }

    private void setUnid(String unid) {
        this.unid = unid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isMissed() {
        return missed;
    }

    public void setMissed(boolean missed) {
        this.missed = missed;
    }

    /**
     * 数据插入之前，自动计算设置unid字段值
     */
    @PrePersist
    public void createUnid(){
        this.unid = SecureUtil.md5(this.target+this.path+this.fileName+this.fileSize);
    }

    /**
     * 数据更新之前，自动计算设置unid字段值
     */
    @PreUpdate
    public void updateUnid(){
        this.unid = SecureUtil.md5(this.target+this.path+this.fileName+this.fileSize);
    }
}
