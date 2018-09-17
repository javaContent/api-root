package com.wd.cloud.docdelivery.entity;

import cn.hutool.crypto.SecureUtil;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author He Zhigang
 * @date 2018/5/3
 * @Description: 文献元数据
 */
@Entity
@Table(name = "literature",uniqueConstraints = {@UniqueConstraint(columnNames = {"unid"})})
public class Literature extends AbstractEntity {

    /**
     * 文献的链接地址
     */
    @Column(name = "doc_href",length = 1000,columnDefinition = "default ''")
    private String docHref = "";
    /**
     * 文献标题
     */
    @NotNull
    @Column(name = "doc_title",length = 1000,columnDefinition = "default ''")
    private String docTitle = "";

    @OneToMany(mappedBy = "literature")
    @OrderBy(value = "gmt_create desc")
    @Where(clause = "audit_status not in (0,2) or audit_status is null")
    private Set<DocFile> docFiles;

    @Column(name = "unid")
    private String unid;
    /**
     * 文献作者
     */
    private String authors;

    /**
     * 发表年份
     */
    @Column(name = "year_of_publication")
    private String yearOfPublication;
    /**
     * doi
     */
    private String doi;
    /**
     * 摘要
     */
    private String summary;

    /**
     * 复用
     */
    @Column(name = "is_reusing", columnDefinition = "tinyint(1) COMMENT '0：未复用，1：已复用'")
    private boolean reusing;

    public String getDocHref() {
        return docHref;
    }

    public void setDocHref(String docHref) {
        this.docHref = docHref;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public Set<DocFile> getDocFiles() {
        return docFiles;
    }

    public void setDocFiles(Set<DocFile> docFiles) {
        this.docFiles = docFiles;
    }

    public boolean isReusing() {
        return reusing;
    }

    public void setReusing(boolean reusing) {
        this.reusing = reusing;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    @PrePersist
    public void createUnid(){
        this.unid = SecureUtil.md5(this.docTitle + this.docHref);
    }

    @PreUpdate
    public void updateUnid(){
        this.unid = SecureUtil.md5(this.docTitle + this.docHref);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("docHref", docHref)
                .append("docTitle", docTitle)
                .append("unid",unid)
                .append("docFiles", docFiles)
                .append("authors", authors)
                .append("yearOfPublication", yearOfPublication)
                .append("doi", doi)
                .append("summary", summary)
                .append("reusing", reusing)
                .append("id", id)
                .append("gmtModified", gmtModified)
                .append("gmtCreate", gmtCreate)
                .toString();
    }
}
