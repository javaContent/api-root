package com.wd.cloud.subanalysis.controller;

import cn.hutool.json.JSONObject;
import com.wd.cloud.commons.model.ResponseModel;
import com.wd.cloud.subanalysis.entity.DocForKeyword;
import com.wd.cloud.subanalysis.service.ZtfxServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/ztfx")
public class ZtfxController {

	@Autowired
	private ZtfxServiceI ztfxService;

	@GetMapping("/ztpc/{id}")
	public ResponseModel<JSONObject> ztpc(@PathVariable String id,
										  @RequestParam int startYear,
										  @RequestParam int endYear) {

		JSONObject pageResult = ztfxService.getZtpc(id, startYear, endYear);

		return ResponseModel.ok(pageResult);
	}
	
	/**
	 * 发文趋势
	 */
	@GetMapping("/fwqs/{id}")
	public ResponseModel<JSONObject> fwqs(@PathVariable String id,
										  @RequestParam int startYear,
										  @RequestParam int endYear){

		startYear = 2012;
		Calendar calendar = Calendar.getInstance();
		endYear = calendar.get(Calendar.YEAR) - 1;
		JSONObject pageResult = ztfxService.getFwqs(id, startYear, endYear);

		return ResponseModel.ok(pageResult);
		
	}
	
	@GetMapping("/tfzt/{id}")
	public ResponseModel<JSONObject> tfzt(@PathVariable String id,
					 @RequestParam int startYear,
					 @RequestParam int endYear){
		startYear = 2012;
		Calendar calendar = Calendar.getInstance();
		endYear = calendar.get(Calendar.YEAR) - 1;
		JSONObject pageResult = ztfxService.getTfzt(id, startYear, endYear);

		return ResponseModel.ok(pageResult);
	}
	
	
	@RequestMapping("/fwqs/more")
	public ResponseModel<JSONObject> getMoreFwqsForKey(@RequestParam String keyword,
								  @RequestParam int startYear,
								  @RequestParam int endYear){
		startYear = 2012;
		Calendar calendar = Calendar.getInstance();
		endYear = calendar.get(Calendar.YEAR) - 1;
		JSONObject pageResult = ztfxService.getMoreFwqsForKey(keyword,startYear,endYear);
		return ResponseModel.ok(pageResult);
	}
	
	
	@GetMapping("/fwqk/{keyword}")
	public ResponseModel<JSONObject> fwqk(@PathVariable String keyword){
		JSONObject pageResult = ztfxService.getFwqk(keyword);
		return ResponseModel.ok(pageResult);
	}

	@GetMapping("/getDocForKey/{jguid}")
	public ResponseModel<List<DocForKeyword>> getDocForKey(@PathVariable String jguid,
														   @RequestParam String keyword){

		List<DocForKeyword> docForKeywords = ztfxService.getDocForKeyword(jguid,keyword);
		return ResponseModel.ok(docForKeywords);
	}
}
