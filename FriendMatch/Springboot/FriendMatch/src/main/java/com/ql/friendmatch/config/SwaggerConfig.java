package com.ql.friendmatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 这里一定要标注你控制器的位置
                .apis(RequestHandlerSelectors.basePackage("com.ql.friendmatch.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * api 信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("伙伴匹配项目")
                .description("伙伴匹配项目接口文档")
                .termsOfServiceUrl("https://github.com/ql")
                .contact(new Contact("ql", "https://github.com/ql", "xxx@qq.com"))
                .version("1.0")
                .build();
    }


}
