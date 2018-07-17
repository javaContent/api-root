package com.wd.cloud.searchserver.repository.elastic.strategy.query;


import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.QueryBuilderStrategyI;

/**
 * 查询hot_journal类型中的authorityDB字段
 * 
 * @author pan
 * 
 */
@Component("hotDB")
public class HotAuthorityDBQueryBuilderStrategy implements QueryBuilderStrategyI {

	@Override
	public QueryBuilder execute(String value, Object otherConstraint) {
		return QueryBuilders.termQuery("authorityDB", value);
	}

}
