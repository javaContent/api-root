package com.wd.cloud.subanalysis.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.wd.cloud.subanalysis.entity.DocForKeyword;
import com.wd.cloud.subanalysis.service.ZtfxServiceI;
import org.apache.commons.codec.digest.DigestUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.HistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.support.IncludeExclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;


@Service("ztfxService")
public class ZtfxServiceImpl implements ZtfxServiceI {

    @Autowired
    private TransportClient transportClient;

    private static String cacheIndex = "wos_ztfx";
    private static String cacheType = "ztfx_cache";

    @Override
    public JSONObject getZtpc(String jguid, int startYear, int endYear) {
        JSONObject pageData = new JSONObject();
        String queryStr = "ztpc&" + jguid + "&" + startYear + "&" + endYear;
        String id = DigestUtils.md5Hex(queryStr);
        GetResponse getResp = transportClient.prepareGet(cacheIndex, cacheType, id).get();
        if (getResp.isExists()) {
            List<Map<String, Object>> nodesData = (List<Map<String, Object>>) getResp.getSource().get("nodes");
            List<Map<String, Object>> edgesData = (List<Map<String, Object>>) getResp.getSource().get("edges");
            if (nodesData.size() > 50) {
                pageData.put("nodes", nodesData.subList(0, 50));
            } else {
                pageData.put("nodes", nodesData);
            }

            List<Map<String, Object>> edgesData2 = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < pageData.getJSONArray("nodes").size(); i++) {
                String nodeName = nodesData.get(i).get("name").toString();
                for (int j = 0; j < edgesData.size(); j++) {
                    if (nodeName.equals(edgesData.get(j).get("source")) || nodeName.equals(edgesData.get(j).get("target"))) {
                        edgesData2.add(edgesData.get(j));
                    }
                }
            }
            pageData.put("edges", edgesData2);
        }
        return pageData;
    }


    @Override
    public JSONObject getFwqs(String jguid, int startYear, int endYear) {
        JSONObject pageData = new JSONObject();
        String queryStr = "fwqs" + "&" + jguid + "&" + startYear + "&" + 2016;
        String id = DigestUtils.md5Hex(queryStr);
        GetResponse getResp = transportClient.prepareGet(cacheIndex, cacheType, id).get();
        if (getResp.isExists()) {
            List<Map<String, Object>> lineDatas = (List<Map<String, Object>>) getResp.getSource().get("lineDatas");
            List<String> legends = new ArrayList<String>();
            if (lineDatas.size() > 50) {
                for (Map<String, Object> lineData : lineDatas.subList(0, 50)) {
                    legends.add(lineData.get("name").toString());
                }
                pageData.put("lineDatas", lineDatas.subList(0, 50));
            } else {
                for (Map<String, Object> lineData : lineDatas) {
                    legends.add(lineData.get("name").toString());
                }
                pageData.put("lineDatas", lineDatas);
            }

            pageData.put("legend", legends);
            pageData.put("xAxisData", getResp.getSource().get("xAxisData"));
        }
        return pageData;
    }

    @Override
    public JSONObject getTfzt(String jguid, int startYear, int endYear) {
        JSONObject pageData = new JSONObject();
        //发送到前台的对象
        Map<Integer, JSONObject> tfztPageDate = new HashMap<Integer, JSONObject>();

        String queryStr = "fwqs" + "&" + jguid + "&" + startYear + "&" + 2016;
        String id = DigestUtils.md5Hex(queryStr);
        GetResponse getResp = transportClient.prepareGet(cacheIndex, cacheType, id).get();
        if (getResp.isExists()) {
            List<Map<String, Object>> lineDatas = (List<Map<String, Object>>) getResp.getSource().get("lineDatas");
            List<Integer> years = (List<Integer>) getResp.getSource().get("xAxisData");

            for (int i = 1; i < years.size(); i++) {
                Integer keyyear = years.get(i);
                JSONArray tfztData = new JSONArray();
                for (Map<String, Object> lineData : lineDatas) {
                    List<Integer> datas = (List<Integer>) lineData.get("data");
                    String key = (String) lineData.get("name");
                    double startYearCount = (double) datas.get(i - 1) == 0 ? 1 : (double) datas.get(i - 1);
                    double endYearCount = datas.get(i);
                    if (endYearCount - startYearCount > 0) {
                        double number = ((endYearCount - startYearCount) / startYearCount) * endYearCount;
                        //格式化计算结果，保留四位小数
                        DecimalFormat df = new DecimalFormat("0.0000");
                        JSONObject tfztObj = new JSONObject();
                        tfztObj.put("key", key);
                        tfztObj.put("value", df.format(number));
                        tfztObj.put("startYearCount", datas.get(i - 1));
                        tfztObj.put("endYearCount", datas.get(i));
                        tfztData.add(tfztObj);
                    }
                }
                JSONObject tfztDataObj = new JSONObject();
                tfztDataObj.put("tfztData", tfztData);
                tfztDataObj.put("sumsize", tfztData.size());
                pageData.put(keyyear+"", tfztDataObj);
            }
            //对突发主题进行排序
            Iterator<String> tfztYearData = pageData.keySet().iterator();
            while (tfztYearData.hasNext()) {
                String yearName = tfztYearData.next();
                JSONArray yearData = pageData.getJSONObject(yearName).getJSONArray("tfztData");
                sortJsonArray(yearData);
                pageData.getJSONObject(yearName).put("tfztData", yearData);
            }
        }
        return pageData;
    }

    @Override
    public JSONObject getFwqk(String keyword) {

        SearchResponse resp = queryFwqk(keyword);

        JSONArray fwqk = new JSONArray();
        Terms jguidAggs = resp.getAggregations().get("JGUID");
        for (Bucket jguidBuckt : jguidAggs.getBuckets()) {
            JSONObject juidObj = new JSONObject();
            juidObj.put("jguid", jguidBuckt.getKeyAsString());
            Terms journalAggs = jguidBuckt.getAggregations().get("JOURNAL");
            for (Bucket journalBuckt : journalAggs.getBuckets()) {
                juidObj.put("journal", journalBuckt.getKeyAsString());
                Nested lineDatasAggs = journalBuckt.getAggregations().get("LINEDATAS");
                Terms keywordAggs = lineDatasAggs.getAggregations().get("NAME");
                for (Bucket keywordBuckt : keywordAggs.getBuckets()) {
                    Terms sumcountAggs = keywordBuckt.getAggregations().get("SUMCOUNT");
                    for (Bucket sumcountBuckt : sumcountAggs.getBuckets()) {
                        juidObj.put("value", sumcountBuckt.getKey());
                    }
                }
            }
            fwqk.add(juidObj);
        }
        sortJsonArray(fwqk);
        List<String> lenged = new ArrayList<String>();
        List<Integer> data = new ArrayList<Integer>();
        int size = fwqk.size() > 20 ? 20 : fwqk.size();
        for (int i = 0; i < size; i++) {
            lenged.add(fwqk.getJSONObject(i).getStr("journal") + "[" + fwqk.getJSONObject(i).getStr("jguid") + "]");
            data.add(fwqk.getJSONObject(i).getInt("value"));
        }
        JSONArray lineDatas = new JSONArray();
        JSONObject lineData = new JSONObject();
        lineData.put("name", keyword);
        lineData.put("data", data);
        lineDatas.add(lineData);
        JSONObject pageData = new JSONObject();
        pageData.put("lineDatas", lineDatas);
        pageData.put("lenged", lenged);
        return pageData;
    }

    private SearchResponse queryFwqk(String keyword) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("legend", keyword));

        TermsAggregationBuilder jguidAggs = AggregationBuilders.terms("JGUID").field("JGuid").size(Integer.MAX_VALUE);

        TermsAggregationBuilder journalAggs = AggregationBuilders.terms("JOURNAL").field("journal");

        NestedAggregationBuilder lineDatas = AggregationBuilders.nested("LINEDATAS", "lineDatas");
        TermsAggregationBuilder keywordAggs = AggregationBuilders.terms("NAME").field("lineDatas.name").includeExclude(new IncludeExclude(keyword, null));
        TermsAggregationBuilder sumcountAggs = AggregationBuilders.terms("SUMCOUNT").field("lineDatas.sumcount");

        jguidAggs.subAggregation(journalAggs.subAggregation(lineDatas.subAggregation(keywordAggs.subAggregation(sumcountAggs))));

        SearchResponse resp = transportClient.prepareSearch(cacheIndex).setTypes(cacheType)
                .setQuery(queryBuilder)
                .setSize(0)
                .addAggregation(jguidAggs)
                .get();

        return resp;
    }

    @Override
    public JSONObject getMoreFwqsForKey(String keyword, int startYear, int endYear) {
        JSONObject pageData = new JSONObject();
        SearchResponse resp = queryFwqsForKey(keyword, startYear, endYear);
        Histogram years = resp.getAggregations().get("YEAR");
        List<Long> datas = new ArrayList<Long>();
        for (Histogram.Bucket bucket : years.getBuckets()) {
            datas.add(bucket.getDocCount());
        }
        List<JSONObject> lineDatas = new ArrayList<JSONObject>();
        JSONObject lineData = new JSONObject();
        lineData.put("data", datas);
        lineData.put("name", keyword);
        lineDatas.add(lineData);
        pageData.put("lineDatas", lineDatas);
        return pageData;
    }

    private SearchResponse queryFwqsForKey(String keyword, int startYear, int endYear) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        queryBuilder.filter(QueryBuilders.rangeQuery("year").from(startYear, true).to(endYear, true))
                .filter(QueryBuilders.termQuery("keywordsDic.rootKeyword.normal", keyword));

        HistogramAggregationBuilder year = AggregationBuilders.histogram("YEAR")
                .field("year")
                .interval(1)
                .extendedBounds(startYear, endYear)
                .minDocCount(0);

        SearchResponse resp = transportClient.prepareSearch("wos_source3.0").setTypes("periodical")
                .setQuery(queryBuilder)
                .setSize(0)
                .addAggregation(year)
                .get();

        return resp;
    }


    /**
     * JSONArray的value属性排序
     *
     * @param arr
     */
    @SuppressWarnings("unchecked")
    private void sortJsonArray(JSONArray arr) {
        Collections.sort(arr.toList(JSONObject.class), new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                double a = o1.getDouble("value");
                double b = o2.getDouble("value");
                if (a == b) {
                    return 0;
                } else if (a > b) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

    /**
     * 检查期刊是否有主题分析数据
     *
     * @param jguid
     * @return
     */
    @Override
    public boolean checkZtfxExists(String jguid) {
        QueryBuilder queryBuilder = QueryBuilders.termQuery("JGuid", jguid);
        SearchResponse resp = transportClient.prepareSearch(cacheIndex).setTypes(cacheType).setQuery(queryBuilder).get();
        if (resp.getHits().getTotalHits() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> hotKeywords(String queryName){
        QueryBuilder queryBuilder = QueryBuilders.termsQuery("queryname.full",queryName+"&2012&2016");
        SearchResponse resp = transportClient.prepareSearch(cacheIndex).setTypes(cacheType).setQuery(queryBuilder).get();
        if (resp.getHits().getTotalHits()>0){
            for (SearchHit hit:resp.getHits().getHits()){
                List<String> legends = (List)hit.getSource().get("legend");
                return legends.subList(0,5);
            }
        }
        return null;
    }

    @Override
    public List<DocForKeyword> getDocForKeyword(String jguid, String keyword) {
        List<DocForKeyword> docForKeywords = new ArrayList<DocForKeyword>();
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("jguid.keyword", jguid))
                .must(QueryBuilders.termQuery("keywordsDic.rootKeyword.normal", keyword));
        SearchResponse resp = transportClient.prepareSearch("wos_source3.0").setTypes("periodical").setQuery(queryBuilder)
                .setSize(5000).get();
        SearchHit[] hits = resp.getHits().getHits();
        if (hits != null){
            for (SearchHit hit:hits){
                DocForKeyword docForKeyword = new DocForKeyword();
                Map<String,Object> doc = hit.getSource();
                docForKeyword.setDocTitile(doc.get("docTitle").toString());
                docForKeyword.setAuthorList((List)doc.get("author"));
                docForKeyword.setYearAndVolAndIssueAndPages(doc.get("year")+" Vol."+doc.get("volume")+" No."+doc.get("issue")+" page."+doc.get("pageNum"));
                docForKeyword.setKeywords((List)doc.get("keywords"));
                List<Map<String,Object>> sourcesList = (List)doc.get("sources");
                for (Map<String,Object> sources:sourcesList){
                    if (Integer.parseInt(sources.get("isOpen")+"")==0){
                        docForKeyword.setSourceUrl(sources.get("url")+"");
                        break;
                    }
                }
                docForKeywords.add(docForKeyword);
            }
        }
        return docForKeywords;
    }

}
