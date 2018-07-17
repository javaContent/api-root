package com.wd.cloud.searchserver.repository.elastic;

import com.wd.cloud.commons.model.ResponseModel;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.List;
import java.util.Map;


/**
 * TransportRepository class
 *
 * @author hezhigang
 * @date 2018/04/08
 */
public interface ElasticRepository {
	
	/**
	 * 创建索引
	 * @param index
	 * @return
	 */
	ResponseModel createIndex(String index);
	
	/**
	 * 创建索引
	 * @param index
	 * @param settings
	 * @return
	 */
	ResponseModel createIndex(String index, Settings settings);
	
	/**
	 * 创建索引
	 * @param index
	 * @param type
	 * @param mapping
	 * @return
	 */
	ResponseModel createIndex(String index, String type, Map<String, Object> mapping);
	
	/**
	 * 创建索引
	 * @param index
	 * @param type
	 * @return
	 */
	ResponseModel createIndex(String index, String type, Settings settings, Map<String, Object> mapping);
	
	/**
	 * matchAll查询
	 * @param index
	 * @param type
	 * @return
	 */
	ResponseModel matchAll(String index, String type);
	
	/**
	 * 根据 doc ID 查询
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	ResponseModel getDocById(String index, String type, String id);
	
	/**
	 * 根据doc ID 跟新一个文档
	 * @param index
	 * @param type
	 * @param id
	 * @param fieldMap
	 * @return
	 */
	ResponseModel updateFieldById(String index, String type, String id, Map<String, Object> fieldMap);
	
	/** 更新 */
	ResponseModel update(UpdateRequest updateRequest);
	
	/**
	 * 检查文档是否存在
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	boolean isExistsById(String index, String type, String id);
	
	/**
	 * 滚动获取所有数据，defaultScrollTime=1000*60ms defaultBatchSize=10
	 * @param index
	 * @param type
	 * @return
	 */
	ResponseModel scrollAll(String index, String type);
	
	/**
	 * 滚动获取所有数据, defaultSize=10
	 * @param index
	 * @param type
	 * @param scrollTime 毫秒ms
	 * @return
	 */
	ResponseModel scrollAll(String index, String type, long scrollTime);
	
	/**
	 * 滚动获取所有数据，defaultScrollTime=1000*60ms
	 * @param index
	 * @param type
	 * @param batchSize 返回的批量条数
	 * @return
	 */
	ResponseModel scrollAll(String index, String type, int batchSize);
	
	/**
	 * 滚动获取所有数据
	 * @param index
	 * @param type
	 * @param scrollTime
	 * @param batchSize
	 * @return
	 */
	ResponseModel scrollAll(String index, String type, long scrollTime, int batchSize);
	
	/**
	 * 滚动查询，返回所有字段
	 * @param index
	 * @param type
	 * @param queryBuilder 查询条件
	 * @return
	 */
	ResponseModel scrollByQuery(String index, String type, QueryBuilder queryBuilder);
	
	/**
	 * 滚动获取所有数据，仅返回指定字段
	 * @param index
	 * @param type
	 * @param returnFields 返回字段列表
	 * @return
	 */
	ResponseModel scrollAllReFields(String index, String type, String[] returnFields);
	
	/**
	 * 滚动查询，仅返回指定字段
	 * @param index
	 * @param type
	 * @param queryBuilder 查询条件
	 * @param returnFields 返回字段列表
	 * @return
	 */
	ResponseModel scrollByQueryReFields(String index, String type, QueryBuilder queryBuilder, String[] returnFields);
	
	/**
	 * 滚动查询，仅返回指定字段
	 * @param index
	 * @param type
	 * @param queryBuilder
	 * @param returnFields
	 * @param batchSize
	 * @return
	 */
	ResponseModel scrollByQueryReFields(String index, String type, QueryBuilder queryBuilder, String[] returnFields, int batchSize);
	
	
	/**
	 *
	 * @param scrollId
	 * @param scrollTime
	 * @return
	 */
	ResponseModel scrollByScrollId(String scrollId, long scrollTime);
	
	
	ResponseModel query(String index, String type, QueryBuilder queryBuilder,QueryBuilder boolFilterBuilder,SortBuilder sortBuilder,AbstractAggregationBuilder aggregation);
	
	
	public ResponseModel<SearchResponse> query(String index, String type, QueryBuilder queryBuilder,QueryBuilder filterBuilder,SortBuilder sortBuilder,List<AbstractAggregationBuilder> aggregationList);
	 /**
     * 检查期刊是否有主题分析数据
     *
     * @param jguid
     * @return
     */
//	public boolean checkZtfxExists(String index, String type,String jguid);
}
