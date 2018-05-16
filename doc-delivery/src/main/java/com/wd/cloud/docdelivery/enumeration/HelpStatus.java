package com.wd.cloud.docdelivery.enumeration;

/**
 * @author He Zhigang
 * @date 2018/5/15
 * @Description:
 */
public enum HelpStatus {
    WAITING("待应助",1),
    AUDITPASS("审核通过",2),
    AUDITNOPASS("审核不通过",3),
    FINISHED("应助完成",4);

    private String statusName;
    private int statusCode;

    private HelpStatus(String statusName,int statusCode){
        this.statusName = statusName;
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }
}
