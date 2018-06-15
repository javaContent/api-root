package com.wd.cloud.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wd.cloud.search.service.SearchService;

@RestController
public class SearchController {
	
	//@Autowired
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
