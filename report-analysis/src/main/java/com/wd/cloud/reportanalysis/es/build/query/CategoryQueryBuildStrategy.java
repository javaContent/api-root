package com.wd.cloud.reportanalysis.es.build.query;


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
@Component("category")
public class CategoryQueryBuildStrategy implements QueryBuilderStrategyI{
	
	
	@Override
	public QueryBuilder execute(QueryCondition queryCondition) {
		String value = queryCondition.getValue();
		return QueryBuilders.termQuery("category", value.trim());
	}

	

}
