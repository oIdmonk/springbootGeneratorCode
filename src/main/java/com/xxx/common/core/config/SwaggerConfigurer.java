package com.xxx.common.core.config;

import com.xxx.common.Const;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 * // * 文档URL：http://localhost:8080/swagger-ui.html (太丑，弃用)
 * 美化后文档地址：http://localhost:8080/doc.html
 *
 * @author xujingyang
 * @date 2018/05/25
 */
@Configuration
@EnableSwagger2
public class SwaggerConfigurer {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        Contact contact = new Contact("徐景洋", Const.BASE_PATH, "");
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("积分制资讯系统 Api接口文档")
                .termsOfServiceUrl(Const.BASE_PATH)
                .contact(contact)
                .version("1.0")
                .build();
    }

}
