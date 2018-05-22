package com.wd.cloud.docdelivery.enums;

/**
 * @author He Zhigang
 * @date 2018/5/22
 * @Description:
 */
public enum GiveTypeEnum {

    AUTO("系统自动应助",0),
    MANAGER("管理员应助",1),
    USER("用户应助",2),
    THIRD("第三方应助",3),
    OTHER("其它渠道应助",4);

    private String name;
    private int code;

    private GiveTypeEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

}
