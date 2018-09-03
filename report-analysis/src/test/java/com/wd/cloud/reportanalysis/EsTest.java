package com.wd.cloud.reportanalysis;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;




@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {
	
	@Autowired
	TransportClient client;
	
	@Test
	public void test() {
		
		try {
			String field = "wosCites";
			
			/**总被引频次   wosCitesAll*/
//			TermsAggregationBuilder termsBuilders = AggregationBuilders.terms(field).field("year").size(Integer.MAX_VALUE).order(Terms.Order.term(false));
//			SearchRequestBuilder searchRequest = client.prepareSearch("resource").setTypes("resourcelabel").addAggregation(termsBuilders)	;
//			SearchResponse searchResponse = searchRequest.execute().actionGet();
//			Terms yearTerms = null;
//			yearTerms = (StringTerms) searchResponse.getAggregations().get(field);
//			Iterator<org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket> iter = (Iterator<Bucket>) yearTerms.getBuckets().iterator();
//			while(iter.hasNext()){
//				Map<String, Object> json = new LinkedMap(); 
//				org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket collegeBucket = iter.next();
//				long yearCount = collegeBucket.getDocCount();
//				System.out.println(collegeBucket.getKey() + "count:" + yearCount);
//			}
			
			/**篇均被引频次   wosCites*/
			TermsAggregationBuilder termsBuilders = AggregationBuilders.terms(field).field("year").size(Integer.MAX_VALUE).order(Terms.Order.term(false));
			AggregationBuilder termsBuilder = AggregationBuilders.sum("wosCites").field("wosCites");
			termsBuilders.subAggregation(termsBuilder);
			SearchRequestBuilder searchRequest = client.prepareSearch("resource").setTypes("resourcelabel").addAggregation(termsBuilders)	;
			SearchResponse searchResponse = searchRequest.execute().actionGet();
			Terms yearTerms = null;
			yearTerms = (StringTerms) searchResponse.getAggregations().get(field);
			Iterator<org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket> iter = (Iterator<Bucket>) yearTerms.getBuckets().iterator();
			while(iter.hasNext()){
				Map<String, Object> json = new LinkedMap(); 
				org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket collegeBucket = iter.next();
				
				double value = 0;
				InternalSum pvTerms = (InternalSum) collegeBucket.getAggregations().asMap().get("wosCites");
				value = pvTerms.getValue();  
				long yearCount = collegeBucket.getDocCount();
				double dou = value/yearCount;
				dou = (double)Math.round(dou*100)/100;
				System.out.println(collegeBucket.getKey() + ";val = " + value + "count:" + yearCount + "avg:" + dou);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
