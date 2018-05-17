package com.wd.cloud.docdelivery.enums;

public enum ProcessTypeEnum {
	
	WAITHANDLE("未处理",0),
	PASS("直接处理",1),
	THIRD("第三方处理",2),
	NORESULT("没有结果",3),
	OTHER("其他途径",4),
	NOBOOKRESULT("没有结果(图书)",5);
	
	
	private String name;
	private int code;
	
	private ProcessTypeEnum(String name,int code) {
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
