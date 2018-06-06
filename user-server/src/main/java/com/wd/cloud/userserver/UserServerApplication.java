package com.wd.cloud.userserver;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * DocDeliveryApplication class
 *
 * @author hezhigang
 * @date 2018/04/08
 */
@EnableSwagger2Doc
@EnableDiscoveryClient
@SpringBootApplication
public class UserServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }
}
