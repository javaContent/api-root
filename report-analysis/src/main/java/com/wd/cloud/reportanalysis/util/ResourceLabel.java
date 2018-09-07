package com.wd.cloud.reportanalysis.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.wd.cloud.reportanalysis.entity.QueryCondition;

import net.sf.json.JSONObject;

/**
 * 封装查询条件
 * @author Administrator
 *
 */
public class ResourceLabel {
	
	private String id;

	private String act;
	
	private String table;
	
	private String scid;
	
	private String category;
	
	private String[] compareScids;
	
	private String[] time;
	
	private String source;
	
	private String signature;
	
	public ResourceLabel(HttpServletRequest request){
		act = request.getParameter("act");
		table = request.getParameter("table");
		scid = request.getParameter("scid");
		category = request.getParameter("category_type");
		
//		compareScids = request.getParameterValues("compare_scids");
//		time = request.getParameterValues("time");
		String compare = request.getParameter("compare_scids");
		String t = request.getParameter("time");
		if(StringUtils.isNotEmpty(compare)) {
			compareScids = compare.replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		}
		if(StringUtils.isNotEmpty(t)) {
			JSONObject json = JSONObject.fromObject(t);
			String start = json.getString("start");
			String end = json.getString("end");
			time = new String[] {start,end};
		}
		
		source = request.getParameter("source");
		signature = request.getParameter("signature");
	}
	
	
	public List<String> getScids() {
		List<String> scids = new ArrayList<>();
		scids.add(scid);
		if(compareScids != null) {
			for (String string : compareScids) {
				scids.add(string);
			}
		}
		return scids;
	}
	
	public String getSource() {
		return source;
	}
	
	public String getAct() {
		return act;
	}
	
	public String getType() {
		String type = "";
		switch (act) {
			case "esi":
				type = "esi";
				break;
			default:
				type = "resourcelabel";
				break;
		}
		return type;
	}
	
	public String getFiled() {
		String filed = "";
		switch (table) {
			case "amount":
				filed = "year";
				break;
			case "jcr":
				filed = "jcr";
				break;
			case "jcr_zky_1":
				filed = "jcr_b";
				break;
			case "jcr_zky_2":
				filed = "jcr_s";
				break;
			case "total_cited":			//总被引频次
				filed = "wosCitesAll";
				break;
			case "paper_cited":			//篇均被引频次
				filed = "wosCites";
				break;
			default:
				filed = "year";
				break;
		}
		
		
//		switch (act) {
//			case "amount":
//				filed = "year";
//				break;
//			case "partition":
//				if(table.equals("jcr")) {
//					filed = "jcr";
//				} else if(table.equals("jcr_zky_1")) {
//					filed = "jcr_b";
//				} else {
//					filed = "jcr_s";
//				}
//				break;
//			case "cited":
//				if(table.equals("total_cited")) {	//总被引频次
//					filed = "wosCitesAll";			//总被引频次
//				} else {
//					filed = "wosCites";
//				}
//				break;
//			default:
//				filed = "year";
//				break;
//		}
		return filed;
	}


	public List<QueryCondition> getQueryList() {
		List<QueryCondition> list = new ArrayList<>();
		if(time != null) list.add(new QueryCondition("time", Arrays.asList(time)));
		if(source != null) list.add(new QueryCondition("source", source));		//收录
		if(category != null && !category.equals("全部领域")) list.add(new QueryCondition("category", category));//esi学科
//		if(signature != null) list.add(new QueryCondition("signature", signature));
		return list;
	}
	
	
	public String toXML(String scid) {
		StringBuilder xml = new StringBuilder();
		xml.append("<params>");
		if(act != null) {
			xml.append("<act>" + act + "</act>");
		}
		if(scid != null) {
			xml.append("<school>" + scid + "</school>");
		}
		if(time != null) {
			xml.append("<time>[" + time[0] + "," + time[1] + "]</time>");
		}
		if(source != null) {
			xml.append("<source>" + source + "</source>");
		}
		if(signature != null) {
			xml.append("<signature>" + signature + "</signature>");
		}
		if(table != null) {
			xml.append("<table>" + table + "</table>");
		}
		xml.append("</params>");
		return xml.toString();
	}


	public String getScid() {
		return scid;
	}


	public String getCategory() {
		return category;
	}


	public String[] getCompareScids() {
		return compareScids;
	}


	public String getSignature() {
		return signature;
	}

}
