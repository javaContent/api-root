package com.wd.cloud.searchserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableCircuitBreaker
public class SearchServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApplication.class, args);
	}
	
//	@Configuration
//	public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
//		@Override
//	    protected void configure(HttpSecurity http) throws Exception {
//	    	http.authorizeRequests().anyRequest().permitAll()  
//	                .and().csrf().disable();
//	    }
//	}

}
