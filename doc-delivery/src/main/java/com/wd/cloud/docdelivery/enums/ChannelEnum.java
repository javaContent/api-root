package com.wd.cloud.docdelivery.enums;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description: 渠道字典
 */
public enum ChannelEnum {

    /**
     * QQ群
     */
    QQ("QQ", 1),
    /**
     * 期刊导航
     */
    SPIS("期刊导航", 2),
    /**
     * 智慧云
     */
    ZHY("智慧云", 3),
    /**
     * CRS
     */
    CRS("CRS", 4);

    private String name;
    private int code;

    private ChannelEnum(String name, int code) {
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
