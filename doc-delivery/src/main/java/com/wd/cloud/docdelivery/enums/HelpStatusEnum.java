package com.wd.cloud.docdelivery.enums;

/**
 * @author He Zhigang
 * @date 2018/5/7
 * @Description: 互助状态
 */
public enum HelpStatusEnum {

	/**
	 * 待应助
	 */
	WAITHELP("待应助",0),
	/**
	 * 审核不通过
	 */
	NOPASS("审核不通过",0),
	/**
	 * 应助中
	 */
	HELPING("应助中",1),
	/**
	 * 待审核
	 */
	WAITAUDIT("待审核",2),
	/**
	 * 审核通过
	 */
	PASS("审核通过",3),
	/**
	 * 应助完成
	 */
	FINISH("应助完成",3);

	
	
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
