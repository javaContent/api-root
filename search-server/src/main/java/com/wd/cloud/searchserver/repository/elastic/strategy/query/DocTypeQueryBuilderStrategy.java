package com.wd.cloud.searchserver.repository.elastic.strategy.query;


import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.QueryBuilderStrategyI;

@Component("docType")
public class DocTypeQueryBuilderStrategy implements QueryBuilderStrategyI {

	@Override
	public QueryBuilder execute(String value, Object otherConstraint) {
		if ("0".equals(value)) {
			return QueryBuilders.termQuery("docType", 9);
		} else {
			return QueryBuilders.termQuery("docType", Integer.parseInt(value));
		}
	}

}
