package com.wd.cloud.docdelivery.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author He Zhigang
 * @date 2018/5/27
 * @Description:
 */
@Entity
@Table(name = "")
public class DocFile extends AbstractDBModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String fileName;

    private String fileType;

    @NotNull
    private Literature literature;

    private Long auditorId;

    private String auditorName;
    /**
     *复用
     */
    @Column(name = "is_reusing")
    private boolean reusing;

    /**
     * 备注
     */
    private String reMark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
