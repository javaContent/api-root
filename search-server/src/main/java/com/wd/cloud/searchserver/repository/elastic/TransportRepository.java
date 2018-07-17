package com.wd.cloud.searchserver.repository.elastic;

import cn.hutool.core.lang.Console;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.searchserver.util.SystemContext;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.engine.DocumentMissingException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * TransportRepository class
 *
 * @author hezhigang
 * @date 2018/04/08
 */
@Repository("transportRepository")
public class TransportRepository implements ElasticRepository{

	private static final Settings DEFAULT_SETTINGS = Settings.builder()
			.put()
			.build();
	
	@Autowired
	TransportClient client;
	
	public TransportClient getClient() {
		return client;
	}
	
	@Override
	public ResponseModel createIndex(String index){
		return createIndex(index,null,null,null);
	}
	
	@Override
	public ResponseModel createIndex(String index, Settings settings){
		return createIndex(index,null,settings,null);
	}
	
	@Override
	public ResponseModel createIndex(String index, String type,Map<String,Object> mapping){
		return createIndex(index,type,null,mapping);
	}
	
	@Override
	public ResponseModel createIndex(String index, String type, Settings settings, Map<String, Object> mapping) {
		CreateIndexRequest createIndexRequest = new CreateIndexRequest();
		createIndexRequest.index(index);
		if (settings != null){
			createIndexRequest.settings(settings);
		}
		if (mapping != null){
			createIndexRequest.mapping(type,mapping);
		}
		CreateIndexResponse response = client.admin().indices().create(createIndexRequest).actionGet(2000);
		return ResponseModel.ok(response);
	}
	
	@Override
	public ResponseModel<SearchResponse> matchAll(String index, String type) {
		SearchResponse response = client.prepareSearch(index).setTypes(type).get();
		return ResponseModel.ok(response);
	}
	
	@Override
	public ResponseModel<GetResponse> getDocById(String index, String type, String id) {
		GetResponse response = client.prepareGet(index,type,id).get();
		return ResponseModel.ok(response);
	}
	
	@Override
	public ResponseModel updateFieldById(String index, String type, String id, Map<String,Object> fieldMap) {
		try{
			RestStatus response = client.prepareUpdate(index,type,id).setDoc(fieldMap).get().status();
			return ResponseModel.ok(response);
		}catch (DocumentMissingException e){
			Console.log("Document：{}未找到",id);
			return ResponseModel.notFound("DocumentMissingException");
		}
	}
	
	@Override
	public ResponseModel<RestStatus> update(UpdateRequest updateRequest) {
		ResponseModel responseModel = new ResponseModel();
		try {
			RestStatus restStatus = client.update(updateRequest).get().status();
			responseModel = ResponseModel.ok(restStatus);
		} catch (InterruptedException e) {
			e.printStackTrace();
			responseModel = ResponseModel.error(500,"InterruptedException");
		} catch (ExecutionException e) {
			e.printStackTrace();
			responseModel = ResponseModel.error(500,"ExecutionException");
		}
		return responseModel;
	}
	
	@Override
	public boolean isExistsById(String index, String type, String id) {
		return client.prepareGet(index,type,id).get().isExists();
	}
	
	@Override
	public ResponseModel<SearchResponse> scrollAll(String index, String type) {
		return scrollAll(index,type,1000*10,10);
	}
	
	@Override
	public ResponseModel<SearchResponse> scrollAll(String index, String type, long timeValue) {
		return scrollAll(index,type,timeValue,10);
	}
	
	@Override
	public ResponseModel<SearchResponse> scrollAll(String index, String type, int batchSize) {
		return scrollAll(index,type,1000*60,batchSize);
	}
	
	@Override
	public ResponseModel<SearchResponse> scrollAll(String index, String type, long timeValue, int batchSize) {
		SearchResponse response = client.prepareSearch(index).setTypes(type)
			.addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
			.setScroll(TimeValue.timeValueMillis(timeValue))
			.setSize(batchSize)
			.get();
		return ResponseModel.ok(response);
	}
	
	@Override
	public ResponseModel<SearchResponse> scrollByQuery(String index, String type, QueryBuilder queryBuilder) {
		SearchResponse response = client.prepareSearch(index).setTypes(type)
			.setQuery(queryBuilder)
			.setScroll(TimeValue.timeValueMillis(1000*60))
			.get();
		return ResponseModel.ok(response);
	}
	
	@Override
	public ResponseModel<SearchResponse> scrollAllReFields(String index, String type, String[] returnFields) {
		return scrollByQueryReFields(index,type,QueryBuilders.matchAllQuery(),returnFields);
	}
	
	@Override
	public ResponseModel<SearchResponse> scrollByQueryReFields(String index, String type, QueryBuilder queryBuilder, String[] returnFields) {
		return scrollByQueryReFields(index,type,queryBuilder,returnFields,10);
	}
	
	@Override
	public ResponseModel<SearchResponse> scrollByQueryReFields(String index, String type, QueryBuilder queryBuilder, String[] returnFields, int batchSize) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(queryBuilder).fetchSource(returnFields, null);
		searchSourceBuilder.size(batchSize);
		SearchResponse response = client.prepareSearch(index).setTypes(type)
			.addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
			.setScroll(TimeValue.timeValueMillis(1000*60))
			.setSource(searchSourceBuilder)
			.get();
		return ResponseModel.ok(response);
	}
	
	@Override
	public ResponseModel<SearchResponse> scrollByScrollId(String scrollId, long scrollTime) {
		SearchResponse response =  client.prepareSearchScroll(scrollId).setScroll(TimeValue.timeValueMillis(scrollTime)).get();
		return ResponseModel.ok(response);
	}
	
	@Override
	public ResponseModel<SearchResponse> query(String index, String type, QueryBuilder queryBuilder,QueryBuilder filterBuilder,SortBuilder sortBuilder,AbstractAggregationBuilder aggregation) {
		SearchRequestBuilder searchRequest = client.prepareSearch(index).setTypes(type);
		if (null != queryBuilder && null != filterBuilder) {
			searchRequest.setQuery(queryBuilder).setPostFilter(filterBuilder);
		} else if (null != queryBuilder) {
			searchRequest.setQuery(queryBuilder);
		} else if (null != filterBuilder) {
			searchRequest.setPostFilter(filterBuilder);
		}
		if(null != sortBuilder) {
			searchRequest.addSort(sortBuilder);
		}
		if(null != aggregation) {
			searchRequest.addAggregation(aggregation);
		}
		 SearchResponse response = searchRequest.setFrom(SystemContext.getOffset())
				 .setSize(SystemContext.getPageSize())
				 .get();
		return ResponseModel.ok(response);
	}
	
	
	@Override
	public ResponseModel<SearchResponse> query(String index, String type, QueryBuilder queryBuilder,QueryBuilder filterBuilder,SortBuilder sortBuilder,List<AbstractAggregationBuilder> aggregationList) {
		SearchRequestBuilder searchRequest = client.prepareSearch(index).setTypes(type);
		if (null != queryBuilder && null != filterBuilder) {
			searchRequest.setQuery(queryBuilder).setPostFilter(filterBuilder);
		} else if (null != queryBuilder) {
			searchRequest.setQuery(queryBuilder);
		} else if (null != filterBuilder) {
			searchRequest.setPostFilter(filterBuilder);
		}
		if(null != sortBuilder) {
			searchRequest.addSort(sortBuilder);
		}
		for (AbstractAggregationBuilder aggregation : aggregationList) {
			searchRequest.addAggregation(aggregation);
		}
		 SearchResponse response = searchRequest.setFrom(SystemContext.getOffset())
				 .setSize(SystemContext.getPageSize())
				 .get();
		return ResponseModel.ok(response);
	}
	
	 /**
     * 检查期刊是否有主题分析数据
     *
     * @param jguid
     * @return
     */
//	@Override
//    public boolean checkZtfxExists(String index, String type,String jguid) {
//        QueryBuilder queryBuilder = QueryBuilders.termQuery("JGuid", jguid);
//        SearchResponse resp = client.prepareSearch(index).setTypes(type).setQuery(queryBuilder).get();
//        if (resp.getHits().getTotalHits() > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}
