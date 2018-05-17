package com.wd.cloud.docdelivery.enums;

public enum HelpStatusEnum {
	
	WAITHELP("待应助",0),
	HELPING("应助中",1),
	WAITAUDIT("待审核",2),
	PASS("审核通过",3),
	NOPASS("审核不通过",0);
	
	
	private String name;
	private int code;
	
	private HelpStatusEnum(String name,int code) {
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
