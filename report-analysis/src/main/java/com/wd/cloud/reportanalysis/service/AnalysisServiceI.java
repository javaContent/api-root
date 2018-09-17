package com.wd.cloud.reportanalysis.service;

import java.util.List;
import java.util.Map;

import com.wd.cloud.reportanalysis.entity.QueryCondition;
import com.wd.cloud.reportanalysis.util.ResourceLabel;

/**
 * @author He Zhigang
 * @date 2018/5/17
 * @Description:
 */
public interface AnalysisServiceI {

	
	public Map<String, Object> amount(List<QueryCondition> list,String filed,String type);
	
	public Map<String, Object> explain(String type);
	
	

}
