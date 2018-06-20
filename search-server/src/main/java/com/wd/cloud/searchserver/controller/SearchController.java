package com.wd.cloud.searchserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wd.cloud.searchserver.service.SearchService;

/**
 * SearchController class
 *
 * @author hezhigang
 * @date 2018/04/08
 */
@RestController
public class SearchController {
	
	@Autowired
	private SearchService service;
	
	@HystrixCommand(fallbackMethod = "hiBack")
	@RequestMapping("/hi")
	public String hi(){
		return "hi";
	}
	
	public String hiBack(){
		return "Hi back 2";
	}

}
