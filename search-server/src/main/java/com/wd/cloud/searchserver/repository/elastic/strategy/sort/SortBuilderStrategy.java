package com.wd.cloud.searchserver.repository.elastic.strategy.sort;

import org.springframework.stereotype.Component;

import com.wd.cloud.searchserver.entity.SearchCondition;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

@Component("sortBuilder")
public class SortBuilderStrategy  {
	
	public SortBuilder<?> getSortBuilde(int sort,String sortField,Integer detailYear) {
		SortBuilder sortBuilder = null;
		switch (sort) {
			case 1: {
				sortBuilder = SortBuilders.fieldSort("year").order(SortOrder.ASC);
				break;
			}
			case 2: {
				sortBuilder = SortBuilders.fieldSort("year").order(SortOrder.DESC);
				break;
			}
			case 3: {
				sortBuilder = SortBuilders.fieldSort("titleSort").order(SortOrder.ASC);
				break;
			}
			case 4: {
				sortBuilder = SortBuilders.fieldSort("titleSort").order(SortOrder.DESC);
				break;
			}
			case 5: {
				sortBuilder = SortBuilders.fieldSort("clickCount").order(SortOrder.DESC);
				break;
			}
			case 6: {
				sortBuilder = SortBuilders.fieldSort("impactFactor").order(SortOrder.DESC);
				break;
			}
			
			case 9:{
				if (StringUtils.isNotEmpty(sortField.trim())) {
					sortBuilder  = SortBuilders.fieldSort("sort." + sortField).order(SortOrder.DESC);
				}
				break;
			}
			case 10:{
				if (StringUtils.isNotEmpty(sortField.trim())) {
					sortBuilder  = SortBuilders.fieldSort("sort." + sortField).order(SortOrder.ASC);
				}
				break;
			}
			case 11:{
				if (StringUtils.isNotEmpty(sortField.trim())) {
					if(detailYear !=null && detailYear >1000){
						sortBuilder  = SortBuilders.fieldSort("sort." + sortField +"|"+ detailYear).order(SortOrder.DESC);
					}else{
						sortBuilder  = SortBuilders.fieldSort("sort." + sortField).order(SortOrder.DESC);
					}
				}
			}
		}
		return sortBuilder;
	}
	
	
	
	
	
	public SortBuilder<?> getSortBuilde(SearchCondition searchCondition) {
		SortBuilder sortBuilder = null;
		// 构建排序条件
		if (0 != searchCondition.getSort()) {
			switch (searchCondition.getSort()) {
			case 1: {
				sortBuilder = SortBuilders.fieldSort("year").order(SortOrder.ASC);
				break;
			}
			case 2: {
				sortBuilder = SortBuilders.fieldSort("year").order(SortOrder.DESC);
				break;
			}
			case 3: {
				sortBuilder = SortBuilders.fieldSort("titleSort").order(SortOrder.ASC);
				break;
			}
			case 4: {
				sortBuilder = SortBuilders.fieldSort("titleSort").order(SortOrder.DESC);
				break;
			}
			case 5: {
				sortBuilder = SortBuilders.fieldSort("clickCount").order(SortOrder.DESC);
				break;
			}
			case 6: {
				sortBuilder = SortBuilders.fieldSort("impactFactor").order(SortOrder.DESC);
				break;
			}
//			case 7: {
//				// 按评价值排序
//				if (SimpleUtil.strNotNull(searchCondition.getSortField())) {
//					if (evalSortSet.contains(searchCondition.getSortField())) {
//						searchRequest.addSort("evalSort." + searchCondition.getSortField(), SortOrder.DESC);
//					}
//				}
//				break;
//			}
//			case 8: {
//				// 学科序号排序
//				if (SimpleUtil.strNotNull(searchCondition.getSortField())) {
//					if (subjectSortSet.contains(searchCondition.getSortField())) {
//						searchRequest.addSort("subjectNumSort." + searchCondition.getSortField(), SortOrder.ASC);
//					}
//				}
//				break;
//			}
			case 9:{
//				if (SimpleUtil.strNotNull(searchCondition.getSortField())) {
				if (StringUtils.isNotEmpty(searchCondition.getSortField().trim())) {
					sortBuilder  = SortBuilders.fieldSort("sort." + searchCondition.getSortField()).order(SortOrder.DESC);
				}
				break;
			}
			case 10:{
//				if (SimpleUtil.strNotNull(searchCondition.getSortField())) {
				if (StringUtils.isNotEmpty(searchCondition.getSortField().trim())) {
					sortBuilder  = SortBuilders.fieldSort("sort." + searchCondition.getSortField()).order(SortOrder.ASC);
				}
				break;
			}
			case 11:{
//				if (SimpleUtil.strNotNull(searchCondition.getSortField())) {
				if (StringUtils.isNotEmpty(searchCondition.getSortField())) {
					if(searchCondition.getDetailYear()!=null && searchCondition.getDetailYear()>1000){
						sortBuilder  = SortBuilders.fieldSort("sort." + searchCondition.getSortField() +"|"+ searchCondition.getDetailYear()).order(SortOrder.DESC);
					}else{
						sortBuilder  = SortBuilders.fieldSort("sort." + searchCondition.getSortField()).order(SortOrder.DESC);
					}
				}
			}
			}
		}
		return sortBuilder;
	}
	
}
