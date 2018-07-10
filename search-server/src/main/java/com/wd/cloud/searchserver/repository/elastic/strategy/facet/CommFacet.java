package com.wd.cloud.searchserver.repository.elastic.strategy.facet;

import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.FacetBuilderStrategyI;
import com.wd.cloud.searchserver.util.SystemContext;


@Component("dbYearDisciplineFacet")
public class CommFacet implements FacetBuilderStrategyI {

	@Override
	public AbstractAggregationBuilder execute(String field) {
		Integer fs=SystemContext.getFacatSize();
		if(fs!=null&&fs>0){
			return AggregationBuilders.terms(field).field(field).size(fs.intValue()).shardSize(100).shardSize(200);
		}else{
			return AggregationBuilders.terms(field).field(field).shardSize(100).shardSize(200);
		}
	}

}
