package com.wd.cloud.subanalysis;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableSwagger2Doc
@EnableDiscoveryClient
@EnableElasticsearchRepositories(basePackages = "com.wd.cloud.subanalysis.repository")
public class SubAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubAnalysisApplication.class, args);
	}
}
