package com.wd.cloud.searchserver.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 唯一,值不为空也不为null,值没有多余空格
 * 
 * @author pan
 * 
 */
public class UniqueList extends ArrayList<String> {

	private static final long serialVersionUID = 1L;
	private List<ConditionInfo> conditionInfoList = new ArrayList<ConditionInfo>(7);

	public void addConditionInfo(ConditionInfo conditionInfo) {
		this.conditionInfoList.add(conditionInfo);
	}

	public List<ConditionInfo> getConditionInfoList() {
		return this.conditionInfoList;
	}

	public ConditionInfo findConditionInfo(String field) {
		for (ConditionInfo info : conditionInfoList) {
			if (field.equals(info.getField())) {
				return info;
			}
		}
		return null;
	}

	public UniqueList() {
		super(3);
	}

	@Override
	public void add(int index, String element) {
		if (null == element || "".equals(element.trim())) {
			return;
		}
		element = element.trim();
		if (!this.contains(element)) {
			super.add(index, element);
		}
	}

	@Override
	public boolean add(String e) {
		if (null == e || "".equals(e.trim())) {
			return false;
		}
		e = e.trim();
		if (!this.contains(e)) {
			return super.add(e);
		}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends String> collection) {
		for (String elment : collection) {
			this.add(elment);
		}
		return true;
	}

}
