package com.wd.cloud.apigateway.config;

import cn.hutool.setting.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author He Zhigang
 * @date 2018/5/17
 * @Description:
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private Setting apiInfo = new Setting("api-info.setting");

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(createApiInfo());
    }

    private ApiInfo createApiInfo(){
        return new ApiInfoBuilder().title(apiInfo.get("title"))
                .description(apiInfo.get("description"))
                .termsOfServiceUrl(apiInfo.get("termsOfServiceUrl"))
                .version(apiInfo.get("version"))
                .contact(new Contact(apiInfo.get("contact.name"),null,apiInfo.get("contact.email")))
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        UiConfiguration uiConfiguration = new UiConfiguration(null, "list", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
        return uiConfiguration;
    }
}
