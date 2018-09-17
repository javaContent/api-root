package com.wd.cloud.reportanalysis.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wd.cloud.reportanalysis.entity.QueryCondition;
import com.wd.cloud.reportanalysis.repository.TransportRepository;
import com.wd.cloud.reportanalysis.service.AnalysisServiceI;
import com.wd.cloud.reportanalysis.util.AggregationBuilderUtil;
import com.wd.cloud.reportanalysis.util.QueryBuilderUtil;

@Service
public class AnalysisService implements AnalysisServiceI{
	
	@Autowired
	TransportRepository transportRepository;

	@Override
	public Map<String, Object> amount(List<QueryCondition> list,String filed,String type) {
		QueryBuilder queryBuilder = QueryBuilderUtil.convertQueryBuilder(list);
		AbstractAggregationBuilder agg = AggregationBuilderUtil.convertQueryBuilder(filed);
		SearchResponse res = transportRepository.query(queryBuilder,null,agg,type);
		return transform(res,filed);
	}
	
	@Override
	public Map<String, Object> explain(String type) {
		SearchResponse res = transportRepository.query(null,null,null,"explain");
		for (SearchHit hit : res.getHits().getHits()) {
			Map<String,Object> source = hit.getSource();
			if(type.equals(source.get("type"))) 
				return source;
		}
		return null;
	}
	
	public Map<String,Object> transform(SearchResponse searchResponse,String filed) {
		Map<String,Object> result = new HashMap<>();
		SearchHits searchHits = searchResponse.getHits();
		long total = searchHits.getTotalHits();
		double cites = 0;
		result.put("total", total);
		Terms yearTerms = (StringTerms) searchResponse.getAggregations().get(filed);
		Iterator<org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket> iter = (Iterator<Bucket>) yearTerms.getBuckets().iterator();
		Map<String,Object> map = new LinkedHashMap<>();
		while(iter.hasNext()){
			Bucket collegeBucket = iter.next();
			String key = collegeBucket.getKey().toString();
			long yearCount = collegeBucket.getDocCount();
			double val = 0; 
			if(filed.equals("wosCites")) {
				InternalSum pvTerms = (InternalSum) collegeBucket.getAggregations().asMap().get("wosCites");
				double value = pvTerms.getValue(); 
				double avg = (double)Math.round(value/yearCount*100)/100;
				val = avg;
				cites += value;
			} else if(filed.equals("wosCitesAll")) {
				InternalSum pvTerms = (InternalSum) collegeBucket.getAggregations().asMap().get("wosCites");
				double value = pvTerms.getValue();  
				cites += value;
				val = value;
			} else {
				val = yearCount;
			}
			if(StringUtils.isNotEmpty(key)) {
				map.put(key,val);
			}
		}
		result.put("list", map);
		if(filed.equals("wosCites") || filed.equals("wosCitesAll")) {
			result.put("cites", cites);
			double avgCites = (double)cites/total;
			avgCites = (double)Math.round(avgCites*100)/100;
			result.put("paper_cites", avgCites);
		}
		return result;
	}


}
