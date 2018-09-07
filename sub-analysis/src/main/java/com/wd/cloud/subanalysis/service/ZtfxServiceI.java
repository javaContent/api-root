package com.wd.cloud.subanalysis.service;

import cn.hutool.json.JSONObject;
import com.wd.cloud.subanalysis.entity.DocForKeyword;

import java.util.List;

public interface ZtfxServiceI {

	/**
	 * 期刊的主题频次
	 * @param startYear
	 * @param endYear
	 * @return
	 */
	public JSONObject getZtpc(String id, int startYear, int endYear);

	/**
	 * 期刊的发文趋势
	 * @param startYear
	 * @param endYear
	 * @return
	 */
	public JSONObject getFwqs(String id, int startYear, int endYear);

	/**
	 * 期刊的突发主题
	 * @param startYear
	 * @param endYear
	 * @return
	 */
	public JSONObject getTfzt(String id, int startYear, int endYear);


	public JSONObject getMoreFwqsForKey(String keyword, int startYear, int endYear);
	/**
	 * 获取关键字的发文期刊
	 * @param keyword
	 * @return
	 */
	public JSONObject getFwqk(String keyword);
	
	/**
	 * 检查是否存在主题分析数据
	 * @param jguid
	 * @return
	 */
	public boolean checkZtfxExists(String jguid);

	/**
	 * 获取热门主题词
	 * @param queryName
	 * @return
	 */
	public List<String> hotKeywords(String queryName);
	/**
	 * 查看期刊关键字所有的文章
	 * @param journal
	 * @param keyword
	 * @return
	 */
	public List<DocForKeyword> getDocForKeyword(String journal, String keyword);

}
