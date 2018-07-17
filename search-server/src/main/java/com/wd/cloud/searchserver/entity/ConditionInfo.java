package com.wd.cloud.searchserver.entity;

public class ConditionInfo {
	private String field;
	private String value;
	private String logic;

	public ConditionInfo() {
	}

	public ConditionInfo(String field, String value, String logic) {
		this.field = field;
		this.value = value;
		this.logic = logic;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}
}
