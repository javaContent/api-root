package com.wd.cloud.docdelivery.model;

/**
 * @author He Zhigang
 * @date 2018/5/24
 * @Description:
 */
public class Md5FileModel {

    /**
     * 文件的MD5名称
     */
    private String name;
    /**
     * 文件后缀
     */
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
