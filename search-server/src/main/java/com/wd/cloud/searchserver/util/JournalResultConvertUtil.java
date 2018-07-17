package com.wd.cloud.searchserver.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import com.wd.cloud.searchserver.entity.SearchResult;


public class JournalResultConvertUtil {

	public static SearchResult convert(SearchResponse searchResponse) {
		SearchResult searchResult = new SearchResult();

		if (null != searchResponse) {
			List<Map<String, Object>> docList = convertDocList(searchResponse);
			searchResult.setDatas(docList);

			Map<String, Map<String, String>> facetMap = convertFacet(searchResponse);
			searchResult.setFacetDatas(facetMap);
		}

		return searchResult;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Map<String, String>> convertFacet(
			SearchResponse searchResponse) {
		Aggregations aggregations = searchResponse.getAggregations();
		if (null != aggregations) {
			Map<String, Aggregation> aggregationMap = aggregations.getAsMap();
			Set<String> facetKeySet = aggregationMap.keySet();
			Map<String, Map<String, String>> facetDatas = new HashMap<String, Map<String, String>>();
			for (String facetKey : facetKeySet) {
				Aggregation aggregation = aggregationMap.get(facetKey);
				Terms terms = (Terms) aggregation;
				Collection<Bucket> bucketColl = (Collection<Bucket>) terms.getBuckets();
				Map<String, String> facetResult = new HashMap<String, String>();
				for (Bucket bucket : bucketColl) {
					facetResult.put(bucket.getKeyAsString(), bucket.getDocCount() + "");
				}
				facetDatas.put(facetKey, facetResult);
			}
			return facetDatas;
		}
		return Collections.EMPTY_MAP;
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> convertDocList(
			SearchResponse searchResponse) {
		List<Map<String, Object>> datas = null;
		SearchHits searchHits = searchResponse.getHits();
		SearchHit[] hitArr = searchHits.getHits();
		if (null != hitArr && hitArr.length > 0) {
			datas = new ArrayList<Map<String, Object>>(hitArr.length);
			for (SearchHit hit : hitArr) {
				Map<String, Object> source = hit.getSource();
				source.put("_id", hit.getId());

				// 获取高亮值
				Map<String, HighlightField> hightLightMap = hit
						.getHighlightFields();
				Set<String> keySet = hightLightMap.keySet();
				String value = null;
				for (String highlightField : keySet) {
					HighlightField highlightValue = hightLightMap
							.get(highlightField);
					value = highlightValue.fragments()[0].string();
					// 将高亮值也放入source中，高亮值的字段名必须符合命名规范(原字段名_highlight)
					source.put(highlightField, value);
				}

				datas.add(source);
			}
		} else {
			datas = Collections.EMPTY_LIST;
		}

		return datas;
	}

}
