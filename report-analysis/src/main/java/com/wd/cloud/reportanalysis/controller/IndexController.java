package com.wd.cloud.reportanalysis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wd.cloud.reportanalysis.entity.QueryCondition;
import com.wd.cloud.reportanalysis.entity.School;
import com.wd.cloud.reportanalysis.service.AnalysisServiceI;
import com.wd.cloud.reportanalysis.service.CxfWebServiceI;
import com.wd.cloud.reportanalysis.service.SchoolServiceI;
import com.wd.cloud.reportanalysis.util.ResourceLabel;
import com.wd.cloud.commons.model.ResponseModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "数据可视化分析", tags = {""})
@RestController
public class IndexController {
	
	@Autowired
	private SchoolServiceI schoolService;
	
	@Autowired
	private AnalysisServiceI analysisService;
	
	@Autowired
	private CxfWebServiceI cxfWebService;
	
	 @ApiOperation(value = "发文量、分区、被引频次对比分析（非esi）")
	 @ApiImplicitParams({
	            @ApiImplicitParam(name = "act", value = "分析类型（发文量、分区、被引频次）", dataType = "String", paramType = "query"),
	            @ApiImplicitParam(name = "table", value = "表名", dataType = "String", paramType = "query"),
	            @ApiImplicitParam(name = "scid", value = "学校scid", dataType = "String", paramType = "query"),
	            @ApiImplicitParam(name = "compareScids", value = "对比 学校", dataType = "String", paramType = "query"),
	            @ApiImplicitParam(name = "time", value = "时间段", dataType = "String", paramType = "query"),
	            @ApiImplicitParam(name = "source", value = "数据类型", dataType = "String", paramType = "query"),
	            @ApiImplicitParam(name = "signature", value = "机构署名", dataType = "String", paramType = "query")
	    })
	@PostMapping("/compare")
	public ResponseModel compare(HttpServletRequest request) {
		ResourceLabel resource = new ResourceLabel(request);
		Map<String,Object> scidMap = new HashMap<>();
		String type = resource.getType();
		resource.getScids().forEach(scid->{
			School school = schoolService.findByScid(Integer.parseInt(scid));
			if(school == null || school.getIndexName() == null || !type.equals("resourcelabel")) {
				List<QueryCondition> list = resource.getQueryList();
				list.add(new QueryCondition("scid", scid));
				if(resource.getSignature() != null) list.add(new QueryCondition("signature", scid, resource.getSignature()));
				scidMap.put(scid, analysisService.amount(list,resource.getFiled(),type));
			} else {
				scidMap.put(scid, cxfWebService.amount(resource.toXML(scid)));
			}
		});
		Map<String,Object> explain = analysisService.explain(type);
		Map<String,Object> result= new HashMap<String,Object>();
		result.put("explain", explain);
		result.put("content", scidMap);
		System.out.println(result);
		return ResponseModel.ok(result);
	}
	
	
}
