package com.noah.crm.cloud.common.spring;

import com.noah.crm.cloud.common.spring.mvc.AppErrorController;
import com.noah.crm.cloud.common.spring.mvc.AppExceptionHandlerController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author xdw9486
 */
@EnableSwagger2
public class WebApplication {

    //error page
    @Bean
    public ErrorController errorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        return new AppErrorController(errorAttributes, serverProperties.getError());
    }

    //exception handler
    @Bean
    public AppExceptionHandlerController appExceptionHandlerController() {
        return new AppExceptionHandlerController();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .select()
                .apis(requestHandler -> {
                    String packageName = requestHandler.getHandlerMethod().getMethod()
                            .getDeclaringClass().getPackage().getName();
                    return packageName.startsWith("com.noah.crm.cloud") && packageName.contains(".web");
                })
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("使用Swagger2构建RESTful APIs")
                .description("用户文档")
                .termsOfServiceUrl("http://localhost:23101/swagger-ui.html")
                .contact(new Contact("david.xu", "", "david.3424@gmail.com"))
                .version("1.0")
                .build();
    }

}