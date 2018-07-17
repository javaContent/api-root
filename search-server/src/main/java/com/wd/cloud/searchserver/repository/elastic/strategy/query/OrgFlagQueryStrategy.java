package com.wd.cloud.searchserver.repository.elastic.strategy.query;


import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.QueryBuilderStrategyI;

/**
 * 用于对hot_journal类型中的orgFlag字段进行检索
 * 
 * @author pan
 * 
 */
@Component("orgFlag")
public class OrgFlagQueryStrategy implements QueryBuilderStrategyI {

	@Override
	public QueryBuilder execute(String value, Object otherConstraint) {
		return QueryBuilders.termQuery("orgFlag", value.trim());
	}

}
