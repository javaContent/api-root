package com.wd.cloud.docdelivery.enums;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description: 处理状态
 */
public enum ProcessTypeEnum {
	/**
	 * 未处理
	 */
	WAITHANDLE("未处理",0),
	/**
	 * 处理完成
	 */
	PASS("直接处理",1),
	/**
	 * 提交第三方处理
	 */
	THIRD("第三方处理",2),
	/**
	 * 无结果
	 */
	NORESULT("没有结果",3),
	/**
	 * 其它途径
	 */
	OTHER("其他途径",4),
	/**
	 * 无结果（图书）
	 */
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
