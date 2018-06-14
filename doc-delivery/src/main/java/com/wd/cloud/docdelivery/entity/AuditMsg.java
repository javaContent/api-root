package com.wd.cloud.docdelivery.entity;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * @author He Zhigang
 * @date 2018/5/22
 * @Description:
 */
@Entity
@Table(name = "audit_msg")
public class AuditMsg extends AbstractEntity {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("msg", msg)
                .append("id", id)
                .append("gmtModified", gmtModified)
                .append("gmtCreate", gmtCreate)
                .toString();
    }
}
