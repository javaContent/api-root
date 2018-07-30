package com.wd.cloud.docdelivery.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

/**
 * @author He Zhigang
 * @date 2018/5/3
 * @Description: 文献元数据
 */
@Entity
@Table(name = "literature",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"doc_href", "doc_title"})})
public class Literature extends AbstractEntity {

    /**
     * 文献的链接地址
     */
    @Column(name = "doc_href")
    private String docHref;
    /**
     * 文献标题
     */
    @Column(name = "doc_title")
    private String docTitle;

    @OneToMany(mappedBy = "literature")
    @OrderBy(value = "gmt_create desc")
    @Where(clause = "audit_status not in (0,2) or audit_status is null")
    private Set<DocFile> docFiles;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("docHref", docHref)
                .append("docTitle", docTitle)
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
