package com.wd.cloud.docdelivery;

import com.wd.cloud.docdelivery.domain.HelpLierature;
import com.wd.cloud.docdelivery.repository.HelpRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * DocDeliveryApplication class
 *
 * @author hezhigang
 * @date 2018/04/08
 */
@EnableDiscoveryClient
@SpringBootApplication
public class DocDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocDeliveryApplication.class, args);

	}
}
