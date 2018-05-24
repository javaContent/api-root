package com.wd.cloud.commons.model;

import cn.hutool.setting.Setting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;

/**
 * @author He Zhigang
 * @date 2018/5/17
 * @Description:
 */

public abstract class AbstractSwaggerModel {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(createApiInfo());
    }

    private ApiInfo createApiInfo() {
        Setting apiInfo = new Setting("api-info.setting");
        return new ApiInfoBuilder().title(apiInfo.get("title"))
                .description(apiInfo.get("description"))
                .termsOfServiceUrl(apiInfo.get("termsOfServiceUrl"))
                .version(apiInfo.get("version"))
                .contact(new Contact(apiInfo.get("contact.name"), null, apiInfo.get("contact.email")))
                .build();
    }

    @Bean
    public UiConfiguration uiConfiguration() {
        //new UiConfiguration(Boolean deepLinking, Boolean displayOperationId, Integer defaultModelsExpandDepth, Integer defaultModelExpandDepth, ModelRendering defaultModelRendering, Boolean displayRequestDuration, DocExpansion docExpansion, Object filter, Integer maxDisplayedTags, OperationsSorter operationsSorter, Boolean showExtensions, TagsSorter tagsSorter, String validatorUrl);
        UiConfiguration uiConfiguration = new UiConfiguration(null, "list", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
        return uiConfiguration;
    }
}
