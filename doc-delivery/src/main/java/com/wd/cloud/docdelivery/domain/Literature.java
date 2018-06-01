package com.wd.cloud.docdelivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * @author He Zhigang
 * @date 2018/5/3
 * @Description: 文献元数据
 */
@Entity
@Table(name = "literature",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"doc_href", "doc_title"})})
public class Literature extends AbstractDBModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
     *复用
     */
    @Column(name = "is_reusing")
    private boolean reusing;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
