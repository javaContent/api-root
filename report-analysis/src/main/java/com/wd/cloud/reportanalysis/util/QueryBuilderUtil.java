package com.wd.cloud.reportanalysis.util;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.wd.cloud.reportanalysis.entity.QueryCondition;
import com.wd.cloud.reportanalysis.es.build.QueryBuilderStrategyI;


public class QueryBuilderUtil {
	
	
	public static QueryBuilder convertQueryBuilder(List<QueryCondition> list) {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		for (QueryCondition condition : list) {
			QueryBuilderStrategyI strategy = (QueryBuilderStrategyI) SpringContextUtil.getBean(condition.getFieldFlag().trim());
//			QueryBuilder queryBuilder = QueryBuilders.termsQuery(condition.getFieldFlag(), condition.getValues());;
			QueryBuilder queryBuilder = strategy.execute(condition);
			switch (condition.getLogic()) {
				case 1: {
					boolQueryBuilder.must(queryBuilder);
					break;
				}
				case 2: {
					boolQueryBuilder.should(queryBuilder);
					break;
				}
				default: {
					boolQueryBuilder.mustNot(queryBuilder);
				}
			}
		}
		return boolQueryBuilder;
		
	}

}
