package com.webconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.util.JwtInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{
    // 这里这么做是为了提前加载, 防止过滤器中@AutoWired注入为空
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    // 自定义过滤规则
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor()).addPathPatterns("/admin/stock/*").excludePathPatterns("/admin/authentication/*");
    }
}
