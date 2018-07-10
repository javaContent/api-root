package com.wd.cloud.searchserver.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchResult {
	
	/**
	 * 文档检索结果
	 */
	private List<Categorydata> info = new ArrayList<Categorydata>();
	
	private List<Categorydata> zkyInfo = new ArrayList<Categorydata>();

	
	/**
	 * 文档检索结果
	 */
	private List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
	/**
	 * 分面检索结果
	 */
	private Map<String, Map<String, String>> facetDatas = new HashMap<String, Map<String, String>>();
	/**
	 * 相关检索结果
	 */
	private Map<String, List<Map<String, Object>>> relatedDatas = new HashMap<String, List<Map<String, Object>>>();

	/**
	 * 总记录数
	 */
	private long total;
	private float time ;
	
	private String subject;

	public SearchResult() {
	}

	public List<Map<String, Object>> getDatas() {
		return datas;
	}

	public void setDatas(List<Map<String, Object>> datas) {
		this.datas = datas;
	}

	public Map<String, Map<String, String>> getFacetDatas() {
		return facetDatas;
	}

	public void setFacetDatas(Map<String, Map<String, String>> facetDatas) {
		this.facetDatas = facetDatas;
	}

	public Map<String, List<Map<String, Object>>> getRelatedDatas() {
		return relatedDatas;
	}

	public void setRelatedDatas(Map<String, List<Map<String, Object>>> relatedDatas) {
		this.relatedDatas = relatedDatas;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public List<Categorydata> getInfo() {
		return info;
	}

	public void setInfo(List<Categorydata> info) {
		this.info = info;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<Categorydata> getZkyInfo() {
		return zkyInfo;
	}

	public void setZkyInfo(List<Categorydata> zkyInfo) {
		this.zkyInfo = zkyInfo;
	}
	
	
}
