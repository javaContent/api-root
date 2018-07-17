package com.wd.cloud.searchserver.repository.elastic.strategy;

import org.elasticsearch.index.query.QueryBuilder;

public interface QueryBuilderStrategyI {

	public QueryBuilder execute(String value, Object otherConstraint);
}
