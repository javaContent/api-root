package com.wd.cloud.searchserver.entity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;



/**
 * 权威数据库收录年信息
 * 
 * @author pan
 * 
 */
public class AuthorityDatabase {

	private Integer id;
	/**
	 * 权威数据库标识
	 */
	private String flag;
	/**
	 * 分区名
	 */
	private String partitionName;
	/**
	 * 多个分区使用';'号分割<br/>
	 * 所有分区
	 */
	private String allPartition;
	/**
	 * 分区前缀
	 */
	private String prefix;
	/**
	 * 分区后缀
	 */
	private String suffix;
	/**
	 * 值越大优先级越高<br/>
	 * 优先级
	 */
	private Integer priority;
	
	/**
	 * 所有的收录年份，以分号分割
	 */
	private String years;
	
	/**
	 * 别名
	 */
	private String alias;
	
	private String intro;
	
	private Boolean hasContent = false;
	
	public Boolean getHasContent() {
		return hasContent;
	}

	public void setHasContent(Boolean hasContent) {
		this.hasContent = hasContent;
	}

	/**
	 * 显示名称
	 * @return
	 */
	public String getDisplay(){
		if(StringUtils.isEmpty(alias)){
			return flag;
		}else{
			return alias;
		}
	}
	
//	public String toJSONString(){
//		return JSONObject.fromObject(this).toString();
//	}
	
	/**
	 * 所有的年份列表
	 * @return
	 */
	public List<String> getYearList(){
		if(years != null){
			List<String> list=Arrays.asList(years.split(";"));
			Collections.reverse(list);
			return list;
		}
		return new ArrayList<String>();
	}
	
	/**
	 * 最新的年份
	 * @return
	 */
	public String getLastYear(){
		List<String> yearsArr=getYearList();
		if(yearsArr.size()>0){
			String year = yearsArr.get(0);
			if(year.contains("-")){
				String[] ys = year.split("-");
				return ys[ys.length-1];
			}else{
				return year;
			}
		}
		return null;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPartitionName() {
		return partitionName;
	}

	public void setPartitionName(String partitionName) {
		if (null != partitionName) {
            partitionName = partitionName.trim();
        }
		this.partitionName = partitionName;
	}

	public String getAllPartition() {
		return allPartition;
	}

	public void setAllPartition(String allPartition) {
		if (null != allPartition) {
            allPartition = allPartition.trim();
        }
		this.allPartition = allPartition;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		if (null != prefix) {
            prefix = prefix.trim();
        }
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		if (null != suffix) {
			suffix = suffix.trim();
		}
		this.suffix = suffix;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		if (null != flag) {
            flag = flag.trim();
        }
		this.flag = flag;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@Override
	public String toString() {
		return "AuthorityDatabase{" +
				"id=" + id +
				", flag='" + flag + '\'' +
				", partitionName='" + partitionName + '\'' +
				", allPartition='" + allPartition + '\'' +
				", prefix='" + prefix + '\'' +
				", suffix='" + suffix + '\'' +
				", priority=" + priority +
				", years='" + years + '\'' +
				", alias='" + alias + '\'' +
				", intro='" + intro + '\'' +
				", hasContent=" + hasContent +
				'}';
	}
}
