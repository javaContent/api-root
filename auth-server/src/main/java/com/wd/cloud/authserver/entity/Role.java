package com.wd.cloud.authserver.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author He Zhigang
 * @date 2018/6/13
 * @Description:
 */
@Entity
@Table(name = "role")
public class Role extends AbstractEntity{

    private String name;

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<Permission> permissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
