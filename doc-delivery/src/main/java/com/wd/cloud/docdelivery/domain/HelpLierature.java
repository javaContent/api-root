package com.wd.cloud.docdelivery.domain;

import org.bson.types.ObjectId;
import org.hibernate.annotations.FetchProfile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/5/11
 * @Description: 文献互助
 */
@Document(collection = "help_lierature")
public class HelpLierature {

    @Id
    private ObjectId id;

    /**
     * 创建时间
     */
    @Field("gmt_create")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Field("gmt_modified")
    private Date gmtModified;

    /**
     * 期刊名称
     */
    @Field("journal_name")
    private String journalName;

    /**
     * 文献的链接地址
     */
    @Field("doc_href")
    private String docHref;
    /**
     * 文献标题
     */
    @Field("doc_title")
    private String docTitle;
    /**
     * 文献作者
     */
    @Field("author_list")
    private String[] authorList;

    /**
     * 发表年份
     */
    @Field("year_of_publication")
    private String yearOfPublication;
    /**
     * dio
     */
    @Field("doi")
    private String doi;
    /**
     * 摘要
     */
    @Field("summary")
    private String summary;

    /**
     * 文献全文的文件名称
     */
    @Field("doc_filename")
    private String docFilename;
    /**
     * 求助记录
     */
    @Field("help_records")
    private List<HelpRecord> helpRecords;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
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

    public String[] getAuthorList() {
        return authorList;
    }

    public void setAuthorList(String[] authorList) {
        this.authorList = authorList;
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

    public List<HelpRecord> getHelpRecords() {
        return helpRecords;
    }

    public void setHelpRecords(List<HelpRecord> helpRecords) {
        this.helpRecords = helpRecords;
    }
}
