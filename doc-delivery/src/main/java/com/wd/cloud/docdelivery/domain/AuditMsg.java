package com.wd.cloud.docdelivery.domain;

import javax.persistence.*;

/**
 * @author He Zhigang
 * @date 2018/5/22
 * @Description:
 */
@Entity
@Table(name = "audit_msg")
public class AuditMsg extends AbstractDBModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String msg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
