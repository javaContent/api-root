package com.wd.cloud.orgserver.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * @author He Zhigang
 * @date 2018/8/10
 * @Description:
 */
@Entity
@Table(name = "edu_org")
public class EduOrg extends AbstractEntity{

    private String orgFlag;
    private String orgName;

    @OneToMany(mappedBy = "eduOrg")
    private Set<IpRange> ipRanges;

}
