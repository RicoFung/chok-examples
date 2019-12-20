package com.webconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import chok.jwt.JwtInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{
	@Autowired
	JwtInterceptor jwtInterceptor;

	// 自定义过滤规则
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(jwtInterceptor).excludePathPatterns("/admin/authenticate/*");
	}
}
