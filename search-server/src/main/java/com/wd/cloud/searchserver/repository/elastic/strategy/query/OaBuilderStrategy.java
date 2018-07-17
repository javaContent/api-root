package com.wd.cloud.searchserver.repository.elastic.strategy.query;


import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.QueryBuilderStrategyI;

/**
 * 开源刊查找
 * 
 * @author pan
 * 
 */
@Component("oa")
public class OaBuilderStrategy implements QueryBuilderStrategyI {

	@Override
	public QueryBuilder execute(String value, Object otherConstraint) {
		return QueryBuilders.termQuery("isOpen", Integer.parseInt(value));
	}

}
