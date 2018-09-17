package com.wd.cloud.subanalysis.entity;

import java.util.List;

/**
 * Created by DimonHo on 2017/12/12.
 */
public class DocForKeyword {
    private String docTitile;
    private String yearAndVolAndIssueAndPages;
    private String sourceUrl;
    private List<String> keywords;
    private List<String> authorList;

    public String getDocTitile() {
        return docTitile;
    }

    public void setDocTitile(String docTitile) {
        this.docTitile = docTitile;
    }

    public String getYearAndVolAndIssueAndPages() {
        return yearAndVolAndIssueAndPages;
    }

    public void setYearAndVolAndIssueAndPages(String yearAndVolAndIssueAndPages) {
        this.yearAndVolAndIssueAndPages = yearAndVolAndIssueAndPages;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<String> authorList) {
        this.authorList = authorList;
    }
}
