package com.webconfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{

    private static final String VERSION = "1.0";

	@Bean
	public Docket apiDocket()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				// 设置basePackage会将包下的所有被@Api标记类的所有方法作为api
				.apis(RequestHandlerSelectors.basePackage("com.admin"))
		        // 只有标记了@ApiOperation的方法才会暴露出给swagger
		        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.securitySchemes(Collections.singletonList(apiKey()))
				.securityContexts(Arrays.asList(securityContext()));
	}

	private ApiInfo apiInfo()
	{
		return new ApiInfoBuilder()
				.title("chok-task的Rest API文档")
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
				.termsOfServiceUrl("")
				.version(VERSION)
				.build();
	}
    
	private ApiKey apiKey()
	{
		 return new ApiKey("Bearer", "Authorization", "header");
	}
	
	private SecurityContext securityContext()
	{
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}
 
	private List<SecurityReference> defaultAuth()
	{
        AuthorizationScope authorizationScope = new AuthorizationScope("web", "access_token");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("Bearer",authorizationScopes));
	}
    
}
