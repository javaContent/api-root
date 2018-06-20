package com.wd.cloud.searchserver.repository.elastic;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONObject;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.searchserver.util.RestClientRequestUtils;
import com.wd.cloud.searchserver.util.RestClientUrlUtils;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * RestClientRepository class
 *
 * @author hezhigang
 * @date 2018/04/08
 */
@Repository("restClientRepository")
public class RestClientRepository implements ElasticRepository {


    @Override
    public ResponseModel<JSONObject> updateFieldById(String index, String type, String id, Map<String, Object> fieldMap) {
        return null;
    }

    @Override
    public ResponseModel<RestStatus> update(UpdateRequest updateRequest) {
        return null;
    }

    @Override
    public boolean isExistsById(String index, String type, String id) {
        return false;
    }

    @Override
    public ResponseModel scrollAll(String index, String type) {
        return scrollAll(index, type, 1000 * 60, 10);
    }

    @Override
    public ResponseModel scrollAll(String index, String type, long scrollTime) {
        return scrollAll(index, type, scrollTime, 10);
    }

    @Override
    public ResponseModel scrollAll(String index, String type, int batchSize) {
        return scrollAll(index, type, 1000 * 60, batchSize);
    }

    @Override
    public ResponseModel scrollAll(String index, String type, long scrollTime, int batchSize) {
        String dsl = "{\"size\":" + batchSize + "}";
        return httpScrollSearch(index, type, scrollTime, dsl);
    }

    private ResponseModel<JSONObject> httpScrollSearch(String index, String type, long scrollTime, String dsl) {
        String url = RestClientUrlUtils.scrollSearchUrl(index, type, scrollTime);
        JSONObject resObj = RestClientRequestUtils.post(url, dsl, 2000);
        if (resObj == null) {
            return ResponseModel.error(0, "未找到数据");
        }
        return ResponseModel.ok(resObj);
    }


    @Override
    public ResponseModel<JSONObject> scrollByQuery(String index, String type, QueryBuilder queryBuilder) {
        return scrollByQueryReFields(index,type, QueryBuilders.matchAllQuery(),null);
    }

    @Override
    public ResponseModel<JSONObject> scrollAllReFields(String index, String type, String[] returnFields) {
        return scrollByQueryReFields(index,type,QueryBuilders.matchAllQuery(),returnFields);
    }

    @Override
    public ResponseModel<JSONObject> scrollByQueryReFields(String index, String type, QueryBuilder queryBuilder, String[] returnFields) {
        return scrollByQueryReFields(index,type,queryBuilder,returnFields,10);
    }

    @Override
    public ResponseModel<JSONObject> scrollByQueryReFields(String index, String type, QueryBuilder queryBuilder, String[] returnFields,int batchSize) {
        String queryDsl = queryToDsl(queryBuilder, returnFields,batchSize);
        return httpScrollSearch(index, type, 1000 * 60, queryDsl);
    }


    @Override
    public ResponseModel<JSONObject> scrollByScrollId(String scrollId, long scrollTime) {
        String url = RestClientUrlUtils.scrollSearchByScrollIdUrl(scrollId,scrollTime);
        JSONObject jsonResp = RestClientRequestUtils.get(url,2000);
        Console.log(jsonResp);
        return ResponseModel.ok(jsonResp);
    }

    private ResponseModel<JSONObject> httpSearch(String index, String type, String queryDsl) {
        String url = RestClientUrlUtils.searchUrl(index, type);
        JSONObject respJson = RestClientRequestUtils.post(url, queryDsl, 2000);
        if (respJson == null) {
            return ResponseModel.error(0, "未找到数据");
        }
        return ResponseModel.ok(respJson);
    }

    @Override
    public ResponseModel createIndex(String index) {
        return null;
    }

    @Override
    public ResponseModel createIndex(String index, Settings settings) {
        return null;
    }

    @Override
    public ResponseModel createIndex(String index, String type, Map<String, Object> mapping) {
        return null;
    }

    @Override
    public ResponseModel createIndex(String index, String type, Settings settings, Map<String, Object> mapping) {
        return null;
    }


    @Override
    public ResponseModel<JSONObject> matchAll(String index, String type) {
        return httpSearch(index, type, "{\"query\":{\"match_all\":{}}}");
    }

    @Override
    public ResponseModel getDocById(String index, String type, String id) {
        String url = RestClientUrlUtils.getDocByIdUrl(index, type, id);
        return ResponseModel.ok(RestClientRequestUtils.get(url, 2000));
    }


    /**
     * queryBuilder对象转字符串
     *
     * @param queryBuilder
     * @return
     */
    private String queryToDsl(QueryBuilder queryBuilder) {
        return "{\"query\":" + queryBuilder.toString() + "}";
    }

    private String queryToDsl(QueryBuilder queryBuilder, String[] returnFields) {
        return queryToDsl(queryBuilder,returnFields,10);
    }

    private String queryToDsl(QueryBuilder queryBuilder, String[] returnFields,int size) {
        return queryToDsl(queryBuilder,returnFields,size,0);
    }

    private String queryToDsl(QueryBuilder queryBuilder, String[] returnFields,int size,int from) {
        String dsl = "{" +
                "\"size\":"+size + "," +
                "\"from\":"+ from + ",";
        if (returnFields != null && returnFields.length!=0){
            dsl += formatReturnFields(returnFields) + ",";
        }
        dsl += queryToDsl(queryBuilder);
        return dsl;
    }

    private String formatReturnFields(String[] returnFields) {
        String result = "\"_source\": [";
        for (int i = 0; i < returnFields.length; i++) {
            result += i < returnFields.length - 1 ? "\"" + returnFields[i] + "\"," : "\"" + returnFields[i] + "\"";
        }
        result += "]";
        return result;
    }
}
