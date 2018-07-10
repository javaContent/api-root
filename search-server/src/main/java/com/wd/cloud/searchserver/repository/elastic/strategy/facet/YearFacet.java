package com.wd.cloud.searchserver.repository.elastic.strategy.facet;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.FacetBuilderStrategyI;


/**
 * 年分面方式
 * 
 * @author pan
 * 
 */
@Component("yearFacet")
public class YearFacet implements FacetBuilderStrategyI {

	@Override
	public AbstractAggregationBuilder execute(String field) {
		return terms("year").field("year").order(Terms.Order.term(false)).size(10).shardSize(20);
	}

}
