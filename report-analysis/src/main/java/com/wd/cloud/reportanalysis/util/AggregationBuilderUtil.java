package com.wd.cloud.reportanalysis.util;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;


public class AggregationBuilderUtil {
	
	
	public static AbstractAggregationBuilder convertQueryBuilder(String filed) {
		if(filed.equals("wosCitesAll") || filed.equals("wosCites")) {		//总被引频次
			TermsAggregationBuilder termsBuilders = AggregationBuilders.terms(filed).field("year").size(Integer.MAX_VALUE).order(Terms.Order.term(true));
			AggregationBuilder termsBuilder = AggregationBuilders.sum("wosCites").field("wosCites");
			return termsBuilders.subAggregation(termsBuilder);
		}
		return terms(filed).field(filed).order(Terms.Order.term(true)).size(Integer.MAX_VALUE).shardSize(20).order(Terms.Order.term(true));
	}
}
