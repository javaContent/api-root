package com.wd.cloud.docdelivery.enums;

public enum ChannelEnum {

	SPIS("期刊导航",1),
	CRS("CRS",4),
	ZHY("智慧云",3),
	QQ("QQ",2);
	
	private String name;
	private int code;
	private ChannelEnum(String name,int code) {
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
