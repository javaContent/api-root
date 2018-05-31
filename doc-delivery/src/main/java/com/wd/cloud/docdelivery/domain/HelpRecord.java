package com.wd.cloud.docdelivery.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Set;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description: 求助记录
 */
@Entity
@Table(name = "help_record",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"helper_email", "literature_id","status"})})
public class HelpRecord extends AbstractDBModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文献ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Literature literature;

    @OneToMany(mappedBy = "helpRecord")
    @OrderBy(value ="gmt_create desc")
    @Where(clause="audit_status != 2")
    private Set<GiveRecord> giveRecords;

    /**
     * 求助的email地址
     */
    @Email
    @Column(name = "helper_email")
    private String helperEmail;

    /**
     * 求助用户ID
     */
    @Column(name = "helper_id")
    private Long helperId;

    /**
     * 求助者名称
     */
    private String helperName;

    /**
     * 求助用户的机构id
     */
    @Column(name = "helper_scid")
    private Long helperScid;

    /**
     * 求助用户的机构名称
     */
    private String helperScname;

    /**
     * 求助IP
     */
    @Column(name = "helper_ip")
    private String helperIp;

    /**
     * 求助渠道，1：QQ，2：SPIS，3：CRS，4：ZHY
     */
    @Column(name = "help_channel", columnDefinition = "tinyint default 0 COMMENT '求助渠道，1：QQ，2：SPIS，3：CRS，4：ZHY'")
    private int helpChannel;


    /**
     * 互助状态
     * 0：待应助，
     * 1：应助中（用户已认领，15分钟内上传文件），
     * 2: 待审核（用户已应助），
     * 3：求助第三方（第三方应助），
     * 4：应助成功（审核通过或管理员应助），
     * 5：应助失败（超过15天无结果）
     */
    @Column(name = "status", columnDefinition = "tinyint default 0 COMMENT '0：待应助， 1：应助中（用户已认领，15分钟内上传文件）， 2: 待审核（用户已应助）， 3：求助第三方（第三方应助）， 4：应助成功（审核通过或管理员应助）， 5：应助失败（超过15天无结果）'")
    private int status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Literature getLiterature() {
        return literature;
    }

    public void setLiterature(Literature literature) {
        this.literature = literature;
    }

    public String getHelperEmail() {
        return helperEmail;
    }

    public void setHelperEmail(String helperEmail) {
        this.helperEmail = helperEmail;
    }

    public Long getHelperId() {
        return helperId;
    }

    public void setHelperId(Long helperId) {
        this.helperId = helperId;
    }

    public Long getHelperScid() {
        return helperScid;
    }

    public void setHelperScid(Long helperScid) {
        this.helperScid = helperScid;
    }

    public String getHelperIp() {
        return helperIp;
    }

    public void setHelperIp(String helperIp) {
        this.helperIp = helperIp;
    }

    public Integer getHelpChannel() {
        return helpChannel;
    }

    public void setHelpChannel(Integer helpChannel) {
        this.helpChannel = helpChannel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHelperScname() {
        return helperScname;
    }

    public void setHelperScname(String helperScname) {
        this.helperScname = helperScname;
    }

    public String getHelperName() {
        return helperName;
    }

    public void setHelperName(String helperName) {
        this.helperName = helperName;
    }

    public Set<GiveRecord> getGiveRecords() {
        return giveRecords;
    }

    public void setGiveRecords(Set<GiveRecord> giveRecords) {
        this.giveRecords = giveRecords;
    }

    public void setHelpChannel(int helpChannel) {
        this.helpChannel = helpChannel;
    }

    @Override
	public String toString() {
		return "HelpRecord [id=" + id + ", literature=" + literature + ", helperEmail=" + helperEmail + ", helperId="
				+ helperId + ", helperName=" + helperName + ", helperScid=" + helperScid + ", helperScname="
				+ helperScname + ", helperIp=" + helperIp + ", helpChannel=" + helpChannel + ", status=" + status + "]";
	}
    
    
}
