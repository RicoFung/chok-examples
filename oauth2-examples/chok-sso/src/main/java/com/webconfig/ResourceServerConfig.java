package com.webconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import chok.oauth2.MyAccessDeniedHandler;
import chok.oauth2.MyOAuth2ExceptionEntryPoint;

// 先MARK掉，否则会拦截/auth2/*的请求，后续再优化
//@Configuration
//@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
    @Override
    public void configure(HttpSecurity http) throws Exception 
    {
        http
		// 创建session时机
		// 我们可以准确地控制什么时机创建session，有以下选项进行控制：
		// ALWAYS – 如果session不存在总是需要创建；
		// IF_REQUIRED – 仅当需要时，创建session(默认配置)；
		// NEVER – 框架从不创建session，但如果已经存在，会使用该session ；
		// STATELESS – Spring Security不会创建session，或使用session；
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // 为所有请求开启安全规则
        .and().requestMatchers().anyRequest()
        // 允许配置匿名用户
        .and().anonymous()
        // 开启请求权限配置
        .and().authorizeRequests()
        // 开启请求权限配置-1.必须满足SpEL表达式为true时才可访问
//      .antMatchers("/oauth2/test/product/**").access("#oauth2.hasScope('read') and hasRole('ROLE_USER')")
//      .antMatchers("/oauth2/test/product/**").access("#oauth2.hasScope('select') and hasRole('ROLE_USER')")
        .antMatchers("/sso/test/product/**").access("#oauth2.hasScope('read')")
        // 开启请求权限配置-2.必须认证/登录过后才可访问
        .antMatchers("/sso/test/order/**").authenticated() 
        ;
    }

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception
	{
		// 自定义资源异常
		resources
		// 401 Invalid access token
		.authenticationEntryPoint(new MyOAuth2ExceptionEntryPoint())
		// 402 insufficient_scope
		.accessDeniedHandler(new MyAccessDeniedHandler());
	}
}
