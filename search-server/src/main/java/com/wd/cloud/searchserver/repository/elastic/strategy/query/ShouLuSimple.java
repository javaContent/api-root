package com.wd.cloud.searchserver.repository.elastic.strategy.query;


import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.QueryBuilderStrategyI;

public class ShouLuSimple implements QueryBuilderStrategyI {

	@Override
	public QueryBuilder execute(String value, Object otherConstraint) {
		return QueryBuilders.termQuery("shouLu.detailList.simple", value);
	}

}
