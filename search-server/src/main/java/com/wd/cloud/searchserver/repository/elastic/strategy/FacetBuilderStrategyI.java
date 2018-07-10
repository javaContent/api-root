package com.wd.cloud.searchserver.repository.elastic.strategy;

import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;

public interface FacetBuilderStrategyI {

	public AbstractAggregationBuilder execute(String field);
}
