package com.wd.cloud.authserver.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * @author He Zhigang
 * @date 2018/6/5
 * @Description:
 */
@Entity
@Table(name = "user")
public class User extends AbstractEntity{

    @Column(name = "login_time")
    private Date loginTime;
    private String email;
    private Boolean forbidden;
    @Column(name = "last_login_ip")
    private String lastLoginIp;
    private String nickname;
    private String phone;
    private String pwd;
    private String qq;
    private Integer sex;
    private String username;
    @Column(name = "register_ip")
    private String registerIp;
    @Column(name = "register_time")
    private Date registerTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private School school;

    @Column(name = "qq_openid")
    private String qqOpenid;
    @Column(name = "wechat_openid")
    private String wechatOpenid;
    @Column(name = "weibo_openid")
    private String weiboOpenid;

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getForbidden() {
        return forbidden;
    }

    public void setForbidden(Boolean forbidden) {
        this.forbidden = forbidden;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWechatOpenid() {
        return wechatOpenid;
    }

    public void setWechatOpenid(String wechatOpenid) {
        this.wechatOpenid = wechatOpenid;
    }

    public String getWeiboOpenid() {
        return weiboOpenid;
    }

    public void setWeiboOpenid(String weiboOpenid) {
        this.weiboOpenid = weiboOpenid;
    }
}
