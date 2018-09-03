package com.wd.cloud.reportanalysis.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryCondition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4626032275466599094L;
	private String fieldFlag;
	private List<String> values;
	private int logic =1;
	private String type;

	public QueryCondition() {
	}
	
	public QueryCondition(String fieldFlag){
		super();
		this.fieldFlag = fieldFlag;
	}
	
	public QueryCondition(String fieldFlag, String value){
		super();
		this.fieldFlag = fieldFlag;
		this.values = Arrays.asList(value);
	}
	
	public QueryCondition(String fieldFlag, String value, String type){
		super();
		this.fieldFlag = fieldFlag;
		this.type = type;
		this.values = Arrays.asList(value);
	}
	
	public QueryCondition(String fieldFlag, String value, int logic){
		this(fieldFlag, value);
		this.logic = logic;
	}
	
	public QueryCondition(String fieldFlag, List<String> values){
		super();
		this.fieldFlag = fieldFlag;
		this.values = values;
	}

	public QueryCondition(String fieldFlag, List<String> values, Object extraCondition) {
		super();
		this.fieldFlag = fieldFlag;
		this.values = values;
	}
	
	public QueryCondition addValue(String value){
		if(this.values == null){
			this.values = new ArrayList<String>();
		}
		this.values.add(value);
		return this;
	}

	public String getFieldFlag() {
		return fieldFlag;
	}

	public QueryCondition setFieldFlag(String fieldFlag) {
		this.fieldFlag = fieldFlag;
		return this;
	}

	public List<String> getValues() {
		if(this.values == null){
			this.values = new ArrayList<String>();
		}
		return values;
	}

	public QueryCondition setValues(List<String> values) {
		this.values = values;
		return this;
	}
	
	public String getValue(){
		return values.get(0);
	}


	public int getLogic() {
		return logic;
	}
	
	public QueryCondition setLogic(Logic logic){
		this.logic = logic.value();
		return this;
	}

	public QueryCondition setLogic(int logic) {
		this.logic = logic;
		return this;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}