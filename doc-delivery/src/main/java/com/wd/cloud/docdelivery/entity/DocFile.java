package com.wd.cloud.docdelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author He Zhigang
 * @date 2018/5/27
 * @Description:
 */
@Entity
@Table(name = "doc_file")
public class DocFile extends AbstractEntity {

    @NotNull
    private String fileName;

    private String fileType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Literature literature;

    private Long auditorId;

    private String auditorName;
    /**
     * 复用
     */
    @Column(name = "is_reusing", columnDefinition = "tinyint default 0 COMMENT '0:未复用，1：已复用'")
    private boolean reusing;

    /**
     * 备注
     */
    private String reMark;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Literature getLiterature() {
        return literature;
    }

    public void setLiterature(Literature literature) {
        this.literature = literature;
    }

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public boolean isReusing() {
        return reusing;
    }

    public void setReusing(boolean reusing) {
        this.reusing = reusing;
    }

    public String getReMark() {
        return reMark;
    }

    public void setReMark(String reMark) {
        this.reMark = reMark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fileName", fileName)
                .append("fileType", fileType)
                .append("literature", literature)
                .append("auditorId", auditorId)
                .append("auditorName", auditorName)
                .append("reusing", reusing)
                .append("reMark", reMark)
                .append("id", id)
                .append("gmtModified", gmtModified)
                .append("gmtCreate", gmtCreate)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocFile docFile = (DocFile) o;
        return reusing == docFile.reusing &&
                Objects.equals(fileName, docFile.fileName) &&
                Objects.equals(fileType, docFile.fileType) &&
                Objects.equals(literature, docFile.literature) &&
                Objects.equals(auditorId, docFile.auditorId) &&
                Objects.equals(auditorName, docFile.auditorName) &&
                Objects.equals(reMark, docFile.reMark);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fileName, fileType, literature, auditorId, auditorName, reusing, reMark);
    }
}