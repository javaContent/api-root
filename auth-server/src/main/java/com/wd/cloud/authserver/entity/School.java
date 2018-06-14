package com.wd.cloud.authserver.entity;


import javax.persistence.*;

/**
 * @author He Zhigang
 * @date 2018/6/5
 * @Description:
 */
@Entity
@Table(name = "school")
public class School extends AbstractEntity{

    private String schoolFlag;
    private String schoolName;
    private String eduFlag;
    @Column(name = "is_enabled", columnDefinition = "tinyint(1)")
    private boolean enabled;

    public String getSchoolFlag() {
        return schoolFlag;
    }

    public void setSchoolFlag(String schoolFlag) {
        this.schoolFlag = schoolFlag;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getEduFlag() {
        return eduFlag;
    }

    public void setEduFlag(String eduFlag) {
        this.eduFlag = eduFlag;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
