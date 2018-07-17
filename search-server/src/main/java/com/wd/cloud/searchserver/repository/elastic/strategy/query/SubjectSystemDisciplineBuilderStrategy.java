package com.wd.cloud.searchserver.repository.elastic.strategy.query;


import org.apache.lucene.queryparser.classic.QueryParserBase;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.QueryBuilderStrategyI;

@Component("scDis")
public class SubjectSystemDisciplineBuilderStrategy implements QueryBuilderStrategyI{

	@Override
	public QueryBuilder execute(String value, Object otherConstraint) {
		//escape  主要就是这一句把特殊字符都转义,那么lucene就可以识别
		return QueryBuilders.boolQuery().should(
				QueryBuilders.regexpQuery("nameNotAnalyzed",".*"+QueryParserBase.escape(value.toLowerCase().replaceAll("&", "").replaceAll(",", ""))+".*")
		).should(
				QueryBuilders.regexpQuery("disciplineNotAnalyzed",".*"+QueryParserBase.escape(value.toLowerCase().replaceAll("&", "").replaceAll(",", ""))+".*")
		).minimumShouldMatch(1);
		//return QueryBuilders.wildcardQuery("discipline", QueryParserBase.escape(value.toLowerCase().replaceAll("&", "").replaceAll(",", "")));
		//return QueryBuilders.fieldQuery("discipline",);
	}

}
