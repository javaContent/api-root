package com.wd.cloud.searchserver.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.searchserver.entity.Categorydata;
import com.wd.cloud.searchserver.entity.SearchCondition;
import com.wd.cloud.searchserver.entity.SearchResult;
import com.wd.cloud.searchserver.entity.UniqueList;
import com.wd.cloud.searchserver.service.SearchServiceI;
import com.wd.cloud.searchserver.util.SystemContext;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * SearchController class
 *
 * @author yangshuaifei
 * @date 2018/04/08
 */
@RestController
public class SearchInterfaceController {
	
	@Autowired
	private SearchServiceI searchInterfaceService;
	
	
	@RequestMapping("/searchInterface/more")
	public ResponseModel searchMore(HttpServletRequest request,
			@RequestParam(value="moreVal", required = false) String moreVal) {
		
		String[] values = moreVal.split(";");
		List<Categorydata> list = null;
		List<SearchResult> resultList = new ArrayList<SearchResult>();
		for (int i = 0; i < values.length; i++) {
			SearchCondition condition = new SearchCondition();
			condition.setValue(values[i]);
			condition.setField("all");
			condition.setSort(0);
			SearchResult searchResult = searchInterfaceService.search(condition);
			searchResult.setDatas(null);
			resultList.add(searchResult);
		}
		return ResponseModel.ok(resultList);
	}
	
	
	@RequestMapping("/searchInterface")
	public ResponseModel search(HttpServletRequest request,
			@RequestParam(value="moreVal", required = false) String moreVal) {
		try {
			long start = System.currentTimeMillis();
			Element root=parseParam(moreVal);
			SearchCondition condition = buildCondition(root);
			SearchResult searchResult = null;
			List<Categorydata> list = null;
			if("id".equals(condition.getField())) {
				searchResult = new SearchResult();
			} else {
				searchResult = searchInterfaceService.search(condition);
				List<Map<String,Object>> listDatas=searchResult.getDatas();
				List<Map<String,Object>> datasList = new ArrayList<Map<String,Object>>();
				for(Map<String,Object> mapDatas : listDatas){
					String jGuid = (String) mapDatas.get("_id");
					if(mapDatas.containsKey("issn")) {
						String issn = mapDatas.get("issn").toString();
						if(issn.length()>9) {
							issn = issn.substring(0,9);
							mapDatas.put("issn", issn);
						}
					}
					datasList.add(mapDatas);
				}
				searchResult.setDatas(datasList);
			}
			
			long end = System.currentTimeMillis();
			searchResult.setTime(end-start);
			String result=JSONObject.fromObject(searchResult).toString();
			return ResponseModel.ok(result);
		} catch (Exception e) {
		}
		return ResponseModel.error();
	}
	
	@RequestMapping("/searchInterface/zhy")
	public ResponseModel searchForZHY(SearchCondition condition){
		try{
//			Element roots=parseParam(requestParam);
			//检索
			long start = System.currentTimeMillis();
//			List<Element> datas = roots.elements("params");
			Map<String,Object> map = new HashMap<String, Object>();
//			for(Element data : datas) {
				List<String> resultList = new ArrayList<String>();
//				Element root=parseParam(data.asXML());
//				SearchCondition condition = buildCondition(root);
				
				String value = condition.getValue();
				
				SearchResult searchResult = null;
				searchResult = searchInterfaceService.search(condition);
				List<Map<String,Object>> listDatas=searchResult.getDatas();
				Map<String,Object> dataMap = listDatas.get(0);
				if(dataMap != null && dataMap.containsKey("shouLu")) {
					List<Map<String,Object>> shoulu = (List<Map<String, Object>>) dataMap.get("shouLu");
					for (Map<String, Object> shouluMap : shoulu) {
						String authorityDatabase = shouluMap.get("authorityDatabase").toString();
						if("SCI-E".equals(authorityDatabase) || "中科院JCR分区(小类)".equals(authorityDatabase) || "中科院JCR分区(大类)".equals(authorityDatabase)){
							List<Map<String,Object>> detailList = (List<Map<String, Object>>) shouluMap.get("detailList");
							for (Map<String, Object> detailMap : detailList) {
								String detail = detailMap.get("detail").toString();
								resultList.add(detail);
							}
						}
					}
					String issn = "" , title = "",hissn = "";
					if(dataMap.containsKey("issn")) {
						issn = dataMap.get("issn").toString();
					}
					if(dataMap.containsKey("history")) {
						List<Map<String, Object>> historyList = (List<Map<String, Object>>) dataMap.get("history");
						for (int i = 0; i < historyList.size(); i++) {
							Map<String, Object> history = historyList.get(i);
							if(history.get("hissn") != null && StringUtils.isNotEmpty(history.get("hissn").toString()))
							hissn = history.get("hissn").toString();
						}
					}
					if(dataMap.containsKey("docTitle")) {
						title = dataMap.get("docTitle").toString();
					}
					//注condition.getValue()如果是issn，会替换成最新的issn（jbase更名记录）
					if(condition.getValue().equals(issn) || condition.getValue().equals(hissn) || condition.getValue().toLowerCase().equals(title.toLowerCase())) {
						map.put(value, resultList);//必须返回传递的issn不能返回最新的issn
					}
				}
//			}
			long end = System.currentTimeMillis();
			System.out.println(end-start);
			String result=JSONObject.fromObject(map).toString();
			return ResponseModel.ok(result);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * 解析查询请求
	 * @param requestParam
	 * @return
	 * @throws DocumentException
	 */
	private Element parseParam(String requestParam)throws DocumentException{
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new StringReader(requestParam));
		Element root = document.getRootElement();
		return root;
	}
	
	/**
	 * 构建查询请求
	 * @param root
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private SearchCondition buildCondition(Element root) {
		SearchCondition condition=new SearchCondition();
		UniqueList queryCdt=new UniqueList();
		UniqueList filterCdt=new UniqueList();
		//Subject
		List subjectNodes = root.selectNodes("/params/subjects/subject");
		for(int i=0;i<subjectNodes.size();i++){
			Node subjectNode=(Node)subjectNodes.get(i);
			String subjectCdt=buildSubjectCdt(subjectNode);
			if(!StringUtils.isEmpty(subjectCdt)){
				queryCdt.add(subjectCdt);
			}
		}
		
		//Field
		String field = "all";
		Node fieldNode = root.selectSingleNode("/params/field");
		if (null != fieldNode) {
			field = fieldNode.getText().trim();
		}
		condition.setField(field);
		//Value
		String value=null;
		Node valueNode=root.selectSingleNode("/params/value");
		if(null!=valueNode){
			value=valueNode.getText().trim();
		}
		condition.setValue(value);
		//shoulu 收录
		Node shouluNode=root.selectSingleNode("/params/shoulu");
		String shoulu=getString(shouluNode);
		if(!StringUtils.isEmpty(shoulu)){
			String[] items=shoulu.split(";");
			for(String str:items){
				filterCdt.add("auDB_3_1_"+str);
			}
		}
		
		//Sort
		Node sortNode=root.selectSingleNode("/params/order");
		Integer sort=getInt(sortNode,0);
		condition.setSort(sort);
		
		Node yearNode=root.selectSingleNode("/params/year");
		Integer year=getInt(yearNode,0);
		condition.setDetailYear(year);
		
		Node orderValueNode=root.selectSingleNode("/params/orderValue");
		if(null!=orderValueNode){
			String orderValue=orderValueNode.getText().trim();
			condition.setSortField(orderValue);
		}
		
		//Lang
		Node langNode=root.selectSingleNode("/params/lang");
		Integer lang=getInt(langNode,0);
		if(lang>0){
			queryCdt.add("lan_3_1_"+lang);
		}
		
		//letter
		Node letterNode=root.selectSingleNode("/params/firstLetter");
		String letter=getString(letterNode);
		if(!StringUtils.isEmpty(letter)){
			queryCdt.add("firstLetter_3_1_"+letter.toLowerCase());
		}
		
		//isoa  --?
		Node oaNode=root.selectSingleNode("/params/isoa");
		Integer isoa=getInt(oaNode,-1);
		if(isoa==1){
			filterCdt.add("oa_3_1_1");
		}
		
		//Size
		Node sizeNode=root.selectSingleNode("/params/size");
		Integer size=getInt(sizeNode,25);
		SystemContext.setPageSize(size);
		
		//Offset
		Node offsetNode=root.selectSingleNode("/params/offset");
		Integer offset=getInt(offsetNode,0);
		SystemContext.setOffset(offset);
		
		Node idNode=root.selectSingleNode("/params/id");
		if(null!=idNode){
			String idValue=idNode.getText().trim();
			condition.setId(idValue);
		}
		
		condition.setFilterCdt(filterCdt);
		condition.setQueryCdt(queryCdt);
		return condition;
	}
	
	private static Integer getInt(final Node node,int defaultValue){
		if(node!=null){
			String value=node.getText();
			if(!StringUtils.isEmpty(value)&&value.trim().matches("\\d+")){
				return Integer.parseInt(value.trim());
			}
		}
		return defaultValue;
	}
	
	private static String getString(final Node node){
		return getString(node,null);
	}
	private static String getString(final Node node,String defaultValue){
		if(node!=null){
			String value=node.getText();
			return value.trim();
		}
		return defaultValue;
	}
	
	private static String getStringByPath(final Node node,String path){
		Node subNode=node.selectSingleNode(path);
		return getString(subNode,null);
	}
	
	/**
	 * 构建查询请求
	 * @param node
	 * @return
	 */
	private String buildSubjectCdt(Node node){
		String name=getStringByPath(node,"name"),value=getStringByPath(node,"value"),
				year=getStringByPath(node,"year"),partition=getStringByPath(node,"partition");
		String queryCdt="";
		if(StringUtils.isEmpty(name)){
			return queryCdt;
		}
		if(StringUtils.isEmpty(partition)){
			queryCdt="shouLuSubjects_4_1_"+name;
			if(!StringUtils.isEmpty(year)){
				queryCdt+="^"+year;
			}
			if(!StringUtils.isEmpty(value)){
				queryCdt+="^"+value;
			}
		}else{
			queryCdt="partition_4_1_"+name;
			if(!StringUtils.isEmpty(year)){
				queryCdt+="^"+year;
			}
			if(!StringUtils.isEmpty(value)){
				queryCdt+="^"+value;
			}
			if(!StringUtils.isEmpty(partition)){
				queryCdt+="^"+partition;
			}
		}
		return queryCdt;
	}
	

}
