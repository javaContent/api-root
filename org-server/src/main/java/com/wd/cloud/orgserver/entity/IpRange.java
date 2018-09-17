package com.wd.cloud.orgserver.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author He Zhigang
 * @date 2018/8/10
 * @Description:
 */
@Entity
@Table(name = "ip_range")
public class IpRange extends AbstractEntity{
    @ManyToOne
    private EduOrg eduOrg;
    private String ipFrom;
    private String ipTo;
}
