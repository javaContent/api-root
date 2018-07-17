package com.wd.cloud.searchserver.entity;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 检索条件
 * 
 * @author pan
 * 
 */
public class SearchCondition {

	/**
	 * 检索类型
	 */
	private String strategyFlag;

	/**
	 * 文档id
	 */
	private String id;

	/**
	 * 检索类型 0、为普通检索(快速检索) 1、为高级检索 2、为专业检索 3、分面统计检索
	 */
	private Integer searchType = 0;
	/**
	 * 文档类型 0、不限制类型
	 */
	private Integer docType = 0;
	/**
	 * 语言 0、表示所有语言 1、表示中文 2、表示外文
	 */
	private Integer lan = 0;
	/**
	 * 检索字段 ,如果为空则表示不限制字段
	 */
	private String field;

	/**
	 * 检索关键词
	 */
	private String value;

	/**
	 * 多查询条件(格式：field_logic_other_value)
	 */
	private UniqueList queryCdt = new UniqueList();

	/**
	 * 多过滤条件
	 */
	private UniqueList filterCdt = new UniqueList();
	/**
	 * 将filterCdt转换为filterMap
	 */
	private Map<String, Set<String>> filterMap = new HashMap<String, Set<String>>();

	/**
	 * 专业查询语句
	 */
	private String proQL = "";

	/**
	 * 排序方式 0、表示默认排序 1、年升序 2、年降序 3、刊名升序 4、刊名降序 6、按影响因子排序 7、按评价值排序 8、按学科序号排9、影响力排序
	 */
	private Integer sort=11;
	/**
	 * 影响力排序值
	 */
	private Integer effectSort;
	/**
	 * 当sort方式为7或8时，该字段会有值
	 */
	private String sortField;

	/**
	 * 分面字段
	 */
	private UniqueList facetList = new UniqueList();

	/**
	 * 检索组件标识，只能由controller负责填充
	 */
	private String searchComponentFlag;

	/**
	 * 视图风格（list/view）
	 */
	private String viewStyle = "view";

	/**
	 * 浏览页将传递这个值(权威数据库)
	 */
	private String authorityDb;
	/**
	 * 浏览页将传递这个值(学科)
	 */
	private String subject;
	/**
	 * 列表页的学科分面结果的超链接会提供该值
	 */
	private String partition;
	/**
	 * 用于最高点击量期刊查询（收录年）
	 */
	private Integer detailYear;
	/**
	 * 如果为null则表示不限制可查看记录数，否则表示限制为50条
	 */
	private Integer limit;

	private String firstLetter;
	
	private String batchId;
	
	@Override
	public String toString(){
		String queryStr="";
		if(!StringUtils.isEmpty(field)){
			queryStr+="&field="+field;
		}
		if(!StringUtils.isEmpty(value)){
			queryStr+="&value="+value;
		}
		if(sort>0){
			queryStr+="&sort="+sort;
		}
		if(!StringUtils.isEmpty(sortField)){
			queryStr+="&sortField="+sortField;
		}
		for(String str : queryCdt){
			queryStr+="&queryCdt="+str;
		}
		for(String str : filterCdt){
			queryStr+="&filterCdt="+str;
		}
		return queryStr;
	}

	public SearchCondition() {
	}

	/**
	 * 检索类型 0、为普通检索(快速检索) 1、为高级检索 2、为专业检索
	 * 
	 * @return 返回值绝对不会为null
	 */
	public Integer getSearchType() {
		return searchType == null ? 0 : searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	/**
	 * 文档类型 0、不限制类型
	 * 
	 * @return 返回值绝对不会为null
	 */
	public Integer getDocType() {
		return docType == null ? 0 : docType;
	}

	public void setDocType(Integer docType) {
		this.docType = docType;
	}

	/**
	 * 检索字段 ,如果为空则表示不限制字段
	 * 
	 * @return 返回值绝对不会为null,并且不会为空
	 */
	public String getField() {
		return field == null ? "all" : field.trim();
	}

	public void setField(String field) {
		this.field = field;
	}

	/**
	 * 检索关键词
	 * 
	 * @return 返回值一定不会为空，且不会有多余空格
	 */
	public String getValue() {
		return value == null ? "" : value.trim();
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 将当前查询条件组装到queryCdt中(加入文档类型、检索字段，检索值，检索语言)
	 */
	public void addDocTypeFieldLan() {
		// 将文档类型加入query条件
		queryCdt.add("docType_3_1_" + this.getDocType());
		// 将当前检索关键字加入到query条件
		queryCdt.add(this.getField() + "_3_1_" + this.getValue());
		if (0 != this.getLan()) {
            queryCdt.add("docLan_3_1_" + this.getLan());
        }
	}
	
	public void addQueryCdt(String cdt){
		queryCdt.add(cdt);
	}

	/**
	 * 
	 * @return 返回值一定不会为null
	 */
	public UniqueList getQueryCdt() {
		return queryCdt;
	}

	/**
	 * (格式：field_logic_other_value)
	 * 
	 * @param queryCdt
	 */
	public void setQueryCdt(UniqueList queryCdt) {
		this.queryCdt = queryCdt;
	}

	public UniqueList getFilterCdt() {
		return filterCdt;
	}

	public void setFilterCdt(UniqueList filterCdt) {
		this.filterCdt = filterCdt;
	}

	/**
	 * 专业查询语句
	 * 
	 * @return 返回值一定不会为空，且不会有多余空格
	 */
	public String getProQL() {
		return proQL == null ? "" : proQL.trim();
	}

	public void setProQL(String proQL) {
		this.proQL = proQL;
	}

	public Integer getSort() {
		if (null == sort) {
			return 0;
		}
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	private QueryStringInfo getStringInfo(String queryCondition) {
		// 格式：field_logic_other_value
		int pos = 0;
		String field = null;
		pos = queryCondition.indexOf("_");
		if (-1 == pos) {
            return null;
        }
		field = queryCondition.substring(0, pos);
		if (StringUtils.isEmpty(field.trim())) {
            return null;
        }
		queryCondition = queryCondition.substring(pos + 1);
		return new QueryStringInfo(queryCondition, field);
	}

	/**
	 * 将filterCdt转换为filterMap
	 * 
	 * @return 返回值绝对不会为null
	 */
	public Map<String, Set<String>> getFilterMap() {
		if (filterMap.isEmpty() && !filterCdt.isEmpty()) {
			for (String singleCondition : filterCdt) {
				// 获取检索条件中的字段条件
				QueryStringInfo fieldInfo = getStringInfo(singleCondition);
				if (null == fieldInfo) {
                    continue;
                }
				// 获取检索条件中的逻辑条件
				QueryStringInfo logicInfo = getStringInfo(fieldInfo.getNewVal());
				if (null == logicInfo) {
                    continue;
                }
				// 获取检索条件中的其它条件
				QueryStringInfo otherInfo = getStringInfo(logicInfo.getNewVal());
				if (null == otherInfo) {
                    continue;
                }
				// 获取检索条件的检索值条件
				String value = otherInfo.getNewVal().trim();
				if (null == value || "".equals(value)) {
                    continue;
                }
				Set<String> valueSet = filterMap.get(fieldInfo.getSubVal());
				if (null == valueSet) {
					valueSet = new LinkedHashSet<String>();
					filterMap.put(fieldInfo.getSubVal(), valueSet);
				}
				if (!valueSet.contains(value)) {
					valueSet.add(value);
				}
				String field=fieldInfo.getSubVal();
				if("impactTo".equals(field) || "impact".equals(field)){
					if(StringUtils.isNotBlank(otherInfo.getSubVal())){
						valueSet.add(otherInfo.getSubVal());
					}
				}
			}
		}
		return filterMap;
	}

//	public String createCacheId() {
//		StringBuilder stringBuilder = new StringBuilder();
//
//		stringBuilder.append(strategyFlag);
//		stringBuilder.append(this.getSearchType()).append(this.getDocType()).append(this.getField()).append(this.getValue());
//		for (String tmp : queryCdt) {
//			stringBuilder.append(tmp);
//		}
//		for (String tmp : filterCdt) {
//			stringBuilder.append(tmp);
//		}
//		stringBuilder.append(this.getProQL());
//		stringBuilder.append(sort);
//
//		return MD5Util.getMD5(stringBuilder.toString().getBytes());
//	}

	public void setFacetList(UniqueList facetList) {
		this.facetList = facetList;
	}

	public UniqueList getFacetList() {
		return facetList;
	}

	/**
	 * 不会返回空和null，默认为快速检索标识,绝对不会包含多余空格
	 * 
	 * @return
	 */
//	public String getStrategyFlag() {
//		if (null == strategyFlag || "".equals(strategyFlag.trim())) {
//			return SearchServiceStrategyEnum.quick_search.value();
//		}
//		return strategyFlag.trim();
//	}

	public void setStrategyFlag(String strategyFlag) {
		this.strategyFlag = strategyFlag;
	}

	public Integer getLan() {
		return lan;
	}

	public void setLan(Integer lan) {
		this.lan = lan;
	}

	public void setFilterMap(Map<String, Set<String>> filterMap) {
		this.filterMap = filterMap;
	}

	public String getSearchComponentFlag() {
		return searchComponentFlag;
	}

	/**
	 * 检索组件标识，只能由controller负责填充
	 * 
	 * @param searchComponentFlag
	 */
	public void setSearchComponentFlag(String searchComponentFlag) {
		this.searchComponentFlag = searchComponentFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getViewStyle() {
		return viewStyle;
	}

	public void setViewStyle(String viewStyle) {
		this.viewStyle = viewStyle;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getAuthorityDb() {
		return authorityDb;
	}

	public void setAuthorityDb(String authorityDb) {
		this.authorityDb = authorityDb;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPartition() {
		return partition;
	}

	public void setPartition(String partition) {
		this.partition = partition;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getEffectSort() {
		return effectSort;
	}

	public void setEffectSort(Integer effectSort) {
		this.effectSort = effectSort;
	}

	public Integer getDetailYear() {
		return detailYear;
	}

	public void setDetailYear(Integer detailYear) {
		this.detailYear = detailYear;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

}
