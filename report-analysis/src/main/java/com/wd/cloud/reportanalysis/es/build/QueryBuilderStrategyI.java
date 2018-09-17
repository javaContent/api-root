package com.wd.cloud.reportanalysis.es.build;

import org.elasticsearch.index.query.QueryBuilder;

import com.wd.cloud.reportanalysis.entity.QueryCondition;

public interface QueryBuilderStrategyI {

	public QueryBuilder execute(QueryCondition queryCondition);
}
