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
@ApiModel(value = "文献求助post对象")
public class HelpModel {

    /**
     * 求助用户ID
     */
    @ApiModelProperty(value = "求助用户ID")
    private Long helperId;

    /**
     * 求助用户名称
     */
    @ApiModelProperty(value = "求助用户名称")
    private String helperName;

    /**
     * 求助渠道
     */
    @ApiModelProperty(value = "求助渠道")
    @NotNull
    private Integer helpChannel;

    /**
     * 求助用户所属机构id
     */
    @ApiModelProperty(value = "求助者所属机构ID")
    private Long helperScid;

    /**
     * 求助用户所属机构名
     */
    @ApiModelProperty(value = "求助者所属机构名称")
    private String helperScname;

    /**
     * 求助文件标题
     */
    @ApiModelProperty(value = "求助文献标题")
    @NotNull
    private String docTitle;

    /**
     * 求助文献连接
     */
    @ApiModelProperty(value = "求助文献链接")
    @URL
    private String docHref;

    /**
     * 求助用户邮箱
     */
    @ApiModelProperty(value = "求助用户邮箱")
    @Email(groups = HELPER_EMAIL.class)
    @NotNull
    private String helperEmail;

    public Long getHelperId() {
        return helperId;
    }

    public void setHelperId(Long helperId) {
        this.helperId = helperId;
    }

    public Integer getHelpChannel() {
        return helpChannel;
    }

    public void setHelpChannel(Integer helpChannel) {
        this.helpChannel = helpChannel;
    }

    public Long getHelperScid() {
        return helperScid;
    }

    public void setHelperScid(Long helperScid) {
        this.helperScid = helperScid;
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

    public String getHelperEmail() {
        return helperEmail;
    }

    public void setHelperEmail(String helperEmail) {
        this.helperEmail = helperEmail;
    }

    public String getHelperName() {
        return helperName;
    }

    public void setHelperName(String helperName) {
        this.helperName = helperName;
    }

    public String getHelperScname() {
        return helperScname;
    }

    public void setHelperScname(String helperScname) {
        this.helperScname = helperScname;
    }


    public interface HELPER_EMAIL{};

    public interface HELPER_ID{};
}
