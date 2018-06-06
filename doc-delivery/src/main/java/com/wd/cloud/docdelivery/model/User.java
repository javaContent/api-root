package com.wd.cloud.docdelivery.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author He Zhigang
 * @date 2018/6/5
 * @Description:
 */
public class User {
    private Long id;

    private Date loginTime;
    private String email;
    private Boolean forbidden;
    private String lastLoginIp;
    private String nickname;
    private String phone;
    private String pwd;
    private String qq;
    private String qqOpenid;
    private String registerIp;
    private Date registerTime;

    private School school;

    private Integer sex;
    private String username;
    private String wechatOpenid;
    private String weiboOpenid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
