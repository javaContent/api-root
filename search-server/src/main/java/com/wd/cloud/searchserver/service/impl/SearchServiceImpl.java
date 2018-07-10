package com.wd.cloud.searchserver.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.searchserver.comm.Comm;
import com.wd.cloud.searchserver.entity.SearchCondition;
import com.wd.cloud.searchserver.entity.SearchResult;
import com.wd.cloud.searchserver.entity.ShouluMap;
import com.wd.cloud.searchserver.repository.elastic.ElasticRepository;
import com.wd.cloud.searchserver.repository.elastic.strategy.sort.SortBuilderStrategy;
import com.wd.cloud.searchserver.service.SearchServiceI;
import com.wd.cloud.searchserver.util.DetailParserUtil;
import com.wd.cloud.searchserver.util.FilterBuilderUtil;
import com.wd.cloud.searchserver.util.JournalResultConvertUtil;
import com.wd.cloud.searchserver.util.QueryBuilderUtil;
import com.wd.cloud.searchserver.util.SearchRequestUtil;
import com.wd.cloud.searchserver.util.SystemContext;

/**
 * @author He Zhigang
 * @date 2018/6/20
 * @Description:
 */
@Service("searchInterfaceService")
public class SearchServiceImpl implements SearchServiceI {
	
	@Autowired
	ElasticRepository transportRepository;
	
	@Autowired
	SortBuilderStrategy sortBulider;
	
	/**
	 * 主查询
	 */
	public SearchResult search(SearchCondition searchCondition) {
		SearchResult searchResult = new SearchResult();
		searchCondition.addDocTypeFieldLan();
		QueryBuilder queryBuilder = QueryBuilderUtil.convertQueryBuilder(searchCondition.getQueryCdt());
		// 构建filter条件
		BoolQueryBuilder filterBuilder = FilterBuilderUtil.convert(searchCondition.getFilterMap());
		// 构建分面条件
		List<AbstractAggregationBuilder> aggregationList = SearchRequestUtil.buildFacetCondition(searchCondition, Collections.EMPTY_LIST);
		SortBuilder sortBuilder = sortBulider.getSortBuilde(searchCondition);
		ResponseModel res = transportRepository.query(Comm.Journal_INDEX, Comm.Journal_TYPE_TITLES, queryBuilder,filterBuilder,sortBuilder,aggregationList);
		SearchResponse response = (SearchResponse) res.getBody();
		List<Map<String, Object>> docList = JournalResultConvertUtil.convertDocList(response);
		searchResult.setDatas(docList);
		searchResult.setTotal(response.getHits().getTotalHits());
		return searchResult;
	}
	
	/**
	 * 学科体系统计
	 * @param searchCondition
	 * @return
	 */
	public Map<String, Map<String, String>> searchDisciplineSystem(SearchCondition searchCondition) {
		SearchCondition disciplineSystemFacetSearchCondition = new SearchCondition();
		disciplineSystemFacetSearchCondition.setDocType(10);
		disciplineSystemFacetSearchCondition.setField("disciplineSystemDiscipline");
		disciplineSystemFacetSearchCondition.setValue(searchCondition.getValue());
		// 只统计最近年
		disciplineSystemFacetSearchCondition.getQueryCdt().add("isNew_3_1_" + 1);
		SystemContext.setFacatSize(200);
		disciplineSystemFacetSearchCondition.addDocTypeFieldLan();
		
		QueryBuilder queryBuilder = QueryBuilderUtil.convertQueryBuilder(searchCondition.getQueryCdt());
		// 构建filter条件
		BoolQueryBuilder filterBuilder = FilterBuilderUtil.convert(searchCondition.getFilterMap());
		// 构建分面条件
		List<AbstractAggregationBuilder> aggregationList = SearchRequestUtil.buildFacetCondition(searchCondition, Arrays.asList("dbYearDiscipline"));
		SortBuilder sortBuilder = sortBulider.getSortBuilde(searchCondition);
		ResponseModel disciplineSystemRes = transportRepository.query(Comm.Journal_INDEX, Comm.Journal_TYPE_DISCIPLINE, queryBuilder,filterBuilder,sortBuilder,aggregationList);
		Map<String, Map<String, String>> facetMap = JournalResultConvertUtil.convertFacet((SearchResponse)disciplineSystemRes.getBody());
		return facetMap;
	}
	
	
	public SearchResult searchSubjectSystem(SearchCondition searchCondition) {
		SearchResult searchResult = new SearchResult();
		searchCondition.getQueryCdt().add("docType_3_1_10");
		SystemContext.setPageSize(5000);
		
		QueryBuilder queryBuilder = QueryBuilderUtil.convertQueryBuilder(searchCondition.getQueryCdt());
		// 构建filter条件
		BoolQueryBuilder filterBuilder = FilterBuilderUtil.convert(searchCondition.getFilterMap());
		// 构建分面条件
		List<AbstractAggregationBuilder> aggregationList = SearchRequestUtil.buildFacetCondition(searchCondition, Collections.EMPTY_LIST);
		SortBuilder sortBuilder = sortBulider.getSortBuilde(searchCondition);
		
		ResponseModel res = transportRepository.query(Comm.Journal_INDEX, Comm.Journal_TYPE_DISCIPLINE, queryBuilder,filterBuilder,sortBuilder,aggregationList);
		SearchResponse response = (SearchResponse) res.getBody();
		List<Map<String, Object>> docList = JournalResultConvertUtil.convertDocList(response);
		searchResult.setDatas(docList);
		searchResult.setTotal(response.getHits().getTotalHits());
		return searchResult;
	}
	
	@Override
	public Map<String, Object> getDoc(String id) {
		ResponseModel<GetResponse> responseModel = transportRepository.getDocById(Comm.Journal_INDEX, Comm.Journal_TYPE_TITLES, id);
		GetResponse resp = responseModel.getBody();
		Map<String, Object> doc=resp.getSource();
		doc.put("_id", id);
		Map<String, ShouluMap> map = DetailParserUtil.parseShoulu((List<Map<String, Object>>) doc.get("shouLu"));
		doc.put("souLu", map);
		return doc;
	}

}
