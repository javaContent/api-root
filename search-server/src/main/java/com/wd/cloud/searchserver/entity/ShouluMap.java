package com.wd.cloud.searchserver.entity;


import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;

public class ShouluMap {
	
	/**所有的年份*/
	private List<String> years;
	
	/**搜有的学科*/
	private List<String> subjects;
	
	private Map<String,String> yearSubject;
	
	/**各年的影响因子*/
	private List<Double> impacts;
	
	/**跟年所在的分区*/
	private List<Integer> partitions;
	private Map<String, List<String>> partitions2;
	
	/**是否有影响因子*/
	private boolean hasImpact=true;
	
	/**是否有分区*/
	private boolean hasPartition=true;
	
	/**分区的前缀*/
	private String prefix;
	
	/**分区的后缀*/
	private String stuffix;

	/**获取最新年份的数据 modified 2018.03.07 by 何志刚*/
	public String getZkyNewDate() {
		if(impacts != null) {
			return JSONArray.fromObject(impacts.subList(impacts.size()-1,impacts.size())).toString();
		} else {
			return null;
		}
	}

	/**获取最新年份的数据 modified 2018.03.07 by 何志刚*/
	public String getZkyNewYear() {
		return JSONArray.fromObject(years.subList(years.size()-1, years.size())).toString();
	}

	public Map<String, String> getYearSubject() {
		return yearSubject;
	}

	public void setYearSubject(Map<String, String> yearSubject) {
		this.yearSubject = yearSubject;
	}

	public String getxAxis() {
		return JSONArray.fromObject(years).toString();
	}

	public String getData(){
		return JSONArray.fromObject(impacts).toString();
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public List<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<String> subjects) {
		this.subjects = subjects;
	}

	public List<Double> getImpacts() {
		return impacts;
	}

	public void setImpacts(List<Double> impacts) {
		this.impacts = impacts;
	}
	
	public List<Integer> getPartitions() {
		return partitions;
	}

	public void setPartitions(List<Integer> partitions) {
		this.partitions = partitions;
	}

	public boolean getHasImpact() {
		return hasImpact;
	}

	public void setHasImpact(boolean hasImpact) {
		this.hasImpact = hasImpact;
	}

	public boolean getHasPartition() {
		return hasPartition;
	}

	public void setHasPartition(boolean hasPartition) {
		this.hasPartition = hasPartition;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getStuffix() {
		return stuffix;
	}

	public void setStuffix(String stuffix) {
		this.stuffix = stuffix;
	}

	public Map<String, List<String>> getPartitions2() {
		return partitions2;
	}

	public void setPartitions2(Map<String, List<String>> partitions2) {
		this.partitions2 = partitions2;
	}
	
	
	
	//------------手机版最多只显示三年
	public String getxAxisM() {
		if(years.size() > 3) {
			return JSONArray.fromObject(years.subList(years.size()-3, years.size())).toString();
		} else {
			return JSONArray.fromObject(years).toString();
		}
	}

	public String getDataM(){	
		if(impacts != null) {
			if(impacts.size() > 3) {
				return JSONArray.fromObject(impacts.subList(impacts.size()-3, impacts.size())).toString();
			} else {
				return JSONArray.fromObject(impacts).toString();
			}
		} else {
			return null;
		}
	}
	
	public List<String> getYearsM() {
		if(years.size() > 3) {
			return years.subList(years.size()-3, years.size());
		} else {
			return years;
		}
	}
	public List<Double> getImpactsM() {
		if(impacts != null) {
			if(impacts.size() > 3) {
				return impacts.subList(impacts.size()-3, impacts.size());
			} else {
				return impacts;
			}
		} else {
			return null;
		}
	}
	
	public Map<String, List<String>> getPartitions2M() {
		if(impacts != null) {
			if(impacts.size() > 3) {
				Iterator<Entry<String, List<String>>> it = partitions2.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String,  List<String>> entry = it.next();
					List<String> list = entry.getValue();
					String key = entry.getKey();
					if(list.size()>3) {
						entry.setValue(list.subList(list.size()-3, list.size()));
					}
				}
				return partitions2;
			} else {
				return partitions2;
			}
		} else {
			return null;
		}
	}

}
