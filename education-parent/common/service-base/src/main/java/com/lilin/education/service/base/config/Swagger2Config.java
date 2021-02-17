package com.lilin.education.service.base.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * swagger配置类，用于生成接口文档，快速测试等
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("webApi")
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build(); //分组;
    }
    @Bean
    public Docket adminApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build(); //分组

    }
    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("网站的api文档")
                .description("本文档描述了在线教育平台网站的api接口定义")
                .version("1.0")
                .contact(new Contact("lilin","htt://atguigu.coom","944576550@qq.com"))
                .build();
    }
    private ApiInfo adminApiInfo(){
        return new ApiInfoBuilder()
                .title("后台管理系统的api文档！")
                .description("本文档描述了谷粒学院后台管理系统的api接口定义")
                .version("1.0")
                .contact(new Contact("lilin","htt://atguigu.coom","944576550@qq.com"))
                .build();
    }
}
