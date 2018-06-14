package com.wd.cloud.commons.vo;

import java.io.Serializable;

/**
 * @author He Zhigang
 * @date 2018/6/13
 * @Description:
 */
public class SchoolVo implements Serializable {
    private Long id;
    private String schoolFlag;
    private String schoolName;
    private String eduFlag;
    private boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
