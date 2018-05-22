package com.wd.cloud.docdelivery.enums;

/**
 * @author He Zhigang
 * @date 2018/5/22
 * @Description:
 */
public enum AuditEnum {

    WAIT("待审核", 0),
    PASS("审核通过", 1),
    NO_PASS("审核不通过", 2);

    private String name;
    private int code;

    private AuditEnum(String name, int code) {
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
