package com.wd.cloud.docdelivery.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * @author He Zhigang
 * @date 2018/5/3
 * @Description: 文献元数据
 */
@Entity
@Table(name="literature")
public class Literature extends AbstractDBModel{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
     * 文献全文的文件名称
     */
    @Column(name = "doc_filename")
    private String docFilename;

    @Column(name = "file_md5")
    private String fileMd5;

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


    public String getDocFilename() {
        return docFilename;
    }

    public void setDocFilename(String docFilename) {
        this.docFilename = docFilename;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }
}
