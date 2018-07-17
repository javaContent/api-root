package com.wd.cloud.searchserver.repository.elastic.strategy.filter;

import java.util.Iterator;
import java.util.Set;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.repository.elastic.strategy.FilterBuilderStrategyI;

@Component("impactToFilter")
public class ImpactToFilterStrategy implements FilterBuilderStrategyI{
	
	@Override
	public BoolQueryBuilder execute(BoolQueryBuilder boolFilterBuilder,Set<String> valueSet) {
		Iterator<String> ite=valueSet.iterator();
		String value=ite.next();
		String field=null;
		if(ite.hasNext()){
			field=ite.next();
		}else{
			field="9";
		}
		return boolFilterBuilder.filter(new RangeQueryBuilder("sort."+field).to(Double.parseDouble(value)));
	}

}
