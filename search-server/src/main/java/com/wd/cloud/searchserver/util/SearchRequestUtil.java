package com.wd.cloud.searchserver.util;

import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;

import com.wd.cloud.searchserver.entity.SearchCondition;
import com.wd.cloud.searchserver.repository.elastic.strategy.FacetBuilderStrategyI;


public class SearchRequestUtil {

//	private static Logger logger = Logger.getLogger(SearchRequestUtil.class);
	
	
	public QueryBuilder queryBuilder(SearchCondition searchCondition) {
		QueryBuilder queryBuilder = QueryBuilderUtil.convertQueryBuilder(searchCondition.getQueryCdt());
		return queryBuilder;
	}
	
	
	public static List<AbstractAggregationBuilder> buildFacetCondition(SearchCondition searchCondition , List<String> facetComponentNames) {
		List<AbstractAggregationBuilder> aggregationList = new ArrayList<AbstractAggregationBuilder>();
		for (String facetFlag : facetComponentNames) {
			FacetBuilderStrategyI facetStrategy = (FacetBuilderStrategyI) SpringContextUtil.getBean(facetFlag.trim()+"Facet");
			if (null == facetStrategy) {
//				if (logger.isDebugEnabled()) {
//					logger.debug("无法找到与[" + facetFlag + "]对应的分面配置!");
//				}
				continue;
			}
			AbstractAggregationBuilder abstractAggregationBuilder = facetStrategy.execute(facetFlag);
			aggregationList.add(abstractAggregationBuilder);
		}
		return aggregationList;
	}

}
