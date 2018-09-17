package com.wd.cloud.reportanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.spring4all.swagger.EnableSwagger2Doc;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2Doc
public class ReportAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportAnalysisApplication.class, args);
	}
}
