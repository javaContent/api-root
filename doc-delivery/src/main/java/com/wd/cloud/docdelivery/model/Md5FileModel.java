package com.wd.cloud.docdelivery.model;

/**
 * @author He Zhigang
 * @date 2018/5/24
 * @Description:
 */
public class Md5FileModel {
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name + "." + type;
    }
}
