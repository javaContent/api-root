package com.wd.cloud.docdelivery.domain;

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
@Table(name = "help_record")
public class HelpRecord extends AbstractDBModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文献ID
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "literature_id")
    private Literature literature;

//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "help_record_id")
//    private Set<GiveRecord> giveRecord;
    /**
     * 求助的email地址
     */
    @Email
    @Column(name = "helper_email")
    private String helperEmail;

    /**
     * 求助用户，未登录用户为null
     */
    @Column(name = "helper_id")
    private Long helperId;

    /**
     * 求助用户的学校id
     */
    @Column(name = "helper_scid")
    private Long helperScid;
    /**
     * 求助IP
     */
    @Column(name = "helper_ip")
    private String helperIp;

    /**
     * 求助渠道，1：QQ，2：SPIS，3：CRS，4：ZHY
     */
    @Column(name = "help_channel", columnDefinition = "tinyint default 1")
    private Integer helpChannel;


    /**
     * 互助状态
     * 0：待应助，
     * 1：应助中（用户已认领，15分钟内上传文件），
     * 2: 待审核（用户已应助），
     * 3：求助第三方（第三方应助），
     * 4：应助成功（审核通过或管理员应助），
     * 5：应助失败（超过15天无结果）
     */
    @Column(name = "status", columnDefinition = "tinyint default 0")
    private Integer status;


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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
