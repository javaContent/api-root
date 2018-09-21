package com.wd.cloud.reportanalysis.es.build.query;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import com.wd.cloud.reportanalysis.entity.QueryCondition;
import com.wd.cloud.reportanalysis.es.build.QueryBuilderStrategyI;

/**
 * 范围检索
 * @author Administrator
 *
 */
@Component("scid")
public class OrgQueryBuildStrategy implements QueryBuilderStrategyI{
	
	
	@Override
	public QueryBuilder execute(QueryCondition queryCondition) {
		String value = queryCondition.getValue();
		return QueryBuilders.termQuery("org", value.trim());
	}

	

}
