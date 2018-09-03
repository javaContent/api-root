package com.wd.cloud.reportanalysis.es.build.query;


import org.elasticsearch.index.query.BoolQueryBuilder;
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
@Component("signature")
public class SignatureQueryBuildStrategy implements QueryBuilderStrategyI{
	
	
	@Override
	public QueryBuilder execute(QueryCondition queryCondition) {
		String value = queryCondition.getValue();
		String type = queryCondition.getType();
		if(type.equals("1")) {
			return QueryBuilders.termQuery("firstOrg", value.trim());
		} else if(type.equals("2")) {
			return QueryBuilders.termQuery("reprintOrg", value.trim());
		} else if(type.equals("4")) {//第一机构或通讯机构 
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			boolQueryBuilder.should(QueryBuilders.termQuery("firstOrg", value.trim()));
			boolQueryBuilder.should(QueryBuilders.termQuery("reprintOrg", value.trim()));
			return boolQueryBuilder;
		} else if(type.equals("5")) {//第一机构且通讯机构
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			boolQueryBuilder.must(QueryBuilders.termQuery("firstOrg", value.trim()));
			boolQueryBuilder.must(QueryBuilders.termQuery("reprintOrg", value.trim()));
			return boolQueryBuilder;
		}
		return QueryBuilders.termQuery("org", value.trim());
	}

	

}
