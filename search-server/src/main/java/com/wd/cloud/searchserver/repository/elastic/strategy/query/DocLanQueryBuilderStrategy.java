package com.wd.cloud.searchserver.repository.elastic.strategy.query;


import java.util.Arrays;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.QueryBuilderStrategyI;

@Component("docLan")
public class DocLanQueryBuilderStrategy implements QueryBuilderStrategyI {

	@Override
	public QueryBuilder execute(String value, Object otherConstraint) {
		if ("1".equals(value)) {
			return QueryBuilders.termsQuery("lan", Arrays.asList(1, 3));
		} else if ("2".equals(value)) {
			return QueryBuilders.termsQuery("lan", Arrays.asList(2, 3));
		}
		return null;
	}

}
