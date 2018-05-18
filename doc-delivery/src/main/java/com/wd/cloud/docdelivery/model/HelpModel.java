package com.wd.cloud.docdelivery.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author He Zhigang
 * @date 2018/5/16
 * @Description:
 */
@ApiModel(value="文献求助对象",description="文献求助对象HelpModel")
public class HelpModel {

    /**
     * 求助用户ID
     */
    @ApiModelProperty(value="求助用户ID",name="helpUserId",example="1233")
    private Long helpUserId;

    /**
     * 后台操作用户ID
     */
    @ApiModelProperty(value="后台操作用户ID",name="processUserId",example="2233")
    private Long processUserId;

    /**
     * 求助渠道
     */
    @NotNull
    @ApiModelProperty(value="求助渠道1:spis,2:crs,3:zhy,4:qq",name="helpChannel",example="1")
    private Integer helpChannel;

    /**
     * 求助用户所属机构
     */
    @ApiModelProperty(value="求助用户所属机构ID",name="helpUserScid",example="60")
    private Long helpUserScid;

    /**
     * 求助文件标题
     */
    @NotNull
    @ApiModelProperty(value="求助文件标题",name="docTitle",example="国际金融风暴对普通人的影响")
    private String docTitle;

    /**
     * 求助文献连接
     */
    @URL
    @ApiModelProperty(value="求助文献连接",name="docHref",example="heep://spis.test.hnlat.com")
    private String docHref;

    /**
     * 求助用户邮箱
     */
    @Email
    @NotNull
    @ApiModelProperty(value="求助用户邮箱",name="helpEmail",example="vampirehgg@qq.com")
    private String helpEmail;

    public Long getHelpUserId() {
        return helpUserId;
    }

    public void setHelpUserId(Long helpUserId) {
        this.helpUserId = helpUserId;
    }

    public Long getProcessUserId() {
        return processUserId;
    }

    public void setProcessUserId(Long processUserId) {
        this.processUserId = processUserId;
    }

    public Integer getHelpChannel() {
        return helpChannel;
    }

    public void setHelpChannel(Integer helpChannel) {
        this.helpChannel = helpChannel;
    }

    public Long getHelpUserScid() {
        return helpUserScid;
    }

    public void setHelpUserScid(Long helpUserScid) {
        this.helpUserScid = helpUserScid;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocHref() {
        return docHref;
    }

    public void setDocHref(String docHref) {
        this.docHref = docHref;
    }

    public String getHelpEmail() {
        return helpEmail;
    }

    public void setHelpEmail(String helpEmail) {
        this.helpEmail = helpEmail;
    }
}
