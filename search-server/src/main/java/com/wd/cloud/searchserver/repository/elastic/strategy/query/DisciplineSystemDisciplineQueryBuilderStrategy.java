package com.wd.cloud.searchserver.repository.elastic.strategy.query;


import org.apache.lucene.queryparser.classic.QueryParserBase;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.QueryBuilderStrategyI;

/**
 * 学科体系的学科检索策略
 * 
 * @author pan
 * 
 */
@Component("disciplineSystemDiscipline")
public class DisciplineSystemDisciplineQueryBuilderStrategy implements QueryBuilderStrategyI {

	@Override
	public QueryBuilder execute(String value, Object otherConstraint) {
		TermQueryBuilder termType = QueryBuilders.termQuery("_type", "discipline_system");
		QueryStringQueryBuilder queryString = QueryBuilders.queryStringQuery(QueryParserBase.escape(value.toLowerCase().replaceAll("&", "").replaceAll(",", ""))).field("discipline.system").field("name").defaultOperator(Operator.AND);
		return QueryBuilders.boolQuery().must(termType).must(queryString);
	}

}
