package com.wd.cloud.searchserver.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import com.wd.cloud.searchserver.repository.elastic.TransportRepository;

public class SynonymsUtil {
	
	public static final String DEFAULT_INDEX = "journal";

	public Set<String> extendOrg(String word) {
		TransportRepository transportRepository = (TransportRepository) SpringContextUtil.getBean("transportRepository");

		Set<String> result = new HashSet<String>();
		result.add(word);

		SearchResponse searchResponse = transportRepository.getClient().prepareSearch(DEFAULT_INDEX).setTypes("orgextend").setSearchType(SearchType.QUERY_AND_FETCH).setPreference("_primary_first")
				.setQuery(QueryBuilders.termQuery("org", word)).execute().actionGet();
		SearchHits searchHits = searchResponse.getHits();
		if (searchHits.getTotalHits() > 0) {
			String schoolNames = searchHits.getHits()[0].getSource().get("org").toString();
			result.addAll(Arrays.asList(schoolNames.split(",")));
		}
		return result;
	}

	public static List<String> analyzer(String word, String analyzerName) {
		TransportRepository transportRepository = (TransportRepository) SpringContextUtil.getBean("transportRepository");

		List<String> result = new ArrayList<String>();

		AnalyzeResponse analyzeResponse = transportRepository.getClient().admin().indices().prepareAnalyze(word).setIndex(DEFAULT_INDEX).setAnalyzer(analyzerName).execute().actionGet();
		Iterator<AnalyzeToken> iter = analyzeResponse.iterator();
		while (iter.hasNext()) {
			AnalyzeToken analyzeToken = iter.next();
			result.add(analyzeToken.getTerm());
		}
		return result;
	}

	public static Set<String> extendsWord(String word) {

		return extendKeyword(word, DEFAULT_INDEX, "titleMainFullField");
	}

	private static Set<String> extendKeyword(String word, String type, String field) {
		TransportRepository transportRepository = (TransportRepository) SpringContextUtil.getBean("transportRepository");

		Set<String> result = new HashSet<String>();
		result.add(word);

		SearchResponse searchResponse = transportRepository.getClient().prepareSearch(DEFAULT_INDEX).setTypes(type).setSearchType(SearchType.QUERY_AND_FETCH).setPreference("_primary_first")
				.setQuery(QueryBuilders.termQuery(field, word)).execute().actionGet();
		SearchHits searchHits = searchResponse.getHits();
		if (searchHits.getTotalHits() > 0) {
			SearchHit[] hits = searchHits.getHits();
			Set<String> keywords = new HashSet<String>();
			for (SearchHit hit : hits) {
				getExtendKeywords(keywords, hit, "titleMain");
				getExtendKeywords(keywords, hit, "kwRelatedWords");
				getExtendKeywords(keywords, hit, "titleVise");
				getExtendKeywords(keywords, hit, "kwOfficalWords");
			}
			result.addAll(keywords);
		}
		return result;
	}

	private static void getExtendKeywords(Set<String> keywords, SearchHit hit, String field) {

		Map<String, Object> doc = hit.getSource();
		Object titleMainObj = doc.get(field);
		if (null != titleMainObj) {
			String[] extendKeywordsArr = titleMainObj.toString().split(";");
			if (extendKeywordsArr.length > 0) {
				for (String word : extendKeywordsArr) {
					if (null != word && !"".equals(word.trim())) {
						keywords.add(word.trim());
					}
				}
			}
		}
	}
}
