package com.wd.cloud.searchserver.repository.elastic.strategy.query;


import org.apache.lucene.queryparser.classic.QueryParserBase;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.QueryBuilderStrategyI;

@Component("docTitle")
public class TitleFieldQueryBuilderStrategy implements QueryBuilderStrategyI {

	@Override
	public QueryBuilder execute(String value, Object otherConstraint) {
		return QueryBuilders.queryStringQuery(QueryParserBase.escape(value)).field("docTitle").field("alias").defaultOperator(Operator.AND);
	}

}
