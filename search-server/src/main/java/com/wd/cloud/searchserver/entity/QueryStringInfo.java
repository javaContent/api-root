package com.wd.cloud.searchserver.entity;

public class QueryStringInfo {
	// 截取之后的值
	private String newVal;
	// 截取到的值
	private String subVal;

	public QueryStringInfo(String newVal, String subVal) {
		this.newVal = newVal;
		this.subVal = subVal;
	}

	public String getNewVal() {
		return newVal;
	}

	public String getSubVal() {
		return subVal;
	}
}
