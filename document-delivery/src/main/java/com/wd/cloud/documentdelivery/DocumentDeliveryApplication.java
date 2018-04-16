package com.wd.cloud.documentdelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * DocumentDeliveryApplication class
 *
 * @author hezhigang
 * @date 2018/04/08
 */
@EnableDiscoveryClient
@SpringBootApplication
public class DocumentDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentDeliveryApplication.class, args);
	}
}
