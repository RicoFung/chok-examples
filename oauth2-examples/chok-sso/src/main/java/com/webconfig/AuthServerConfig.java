package com.webconfig;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import chok.common.BeanFactory;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter
{  
	@Autowired
	AuthenticationManager	authenticationManager;
	@Autowired
	RedisConnectionFactory	redisConnectionFactory;
	@Autowired
	BCryptPasswordEncoder	passwordEncoder;
	@Autowired
	UserDetailsService		userDetailsService;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception
	{
		// jdbc 
		clients.jdbc((DataSource) BeanFactory.getBean("dataSourceMybatisOracle"));
		// 配置两个客户端,一个用于password认证一个用于client认证
//		clients.inMemory()
//		.withClient("client_1")
//		.authorizedGrantTypes("client_credentials", "refresh_token")
//		.scopes("select").autoApprove(true)
//		.authorities("client")
//		.secret(passwordEncoder.encode("123456"))
//		.and()
//		.withClient("client_2")
//		.authorizedGrantTypes("password", "refresh_token")
//		.scopes("select").autoApprove(true)
//		.authorities("client")
//		.secret(passwordEncoder.encode("123456"))
//		.and()
//		.withClient("museum-api")
//		.authorizedGrantTypes("password", "refresh_token")
//		.scopes("select").autoApprove(true)
//		.authorities("client")
//		.secret(passwordEncoder.encode("123456"))
//		;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
	{
		endpoints
		// 指定token存储方式
		.tokenStore((TokenStore) BeanFactory.getBean("tokenStore"))
		// 使用password模式，必须设置
		.authenticationManager(authenticationManager)
		// 允许访问token端点的请求方法
		.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
		// refresh_token需要注入userDetailsService
		.userDetailsService(userDetailsService)
		;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception
	{
		oauthServer
		.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()")
		//enable client to get the authenticated when using the /oauth/token to get a access token
		//there is a 401 authentication is required if it doesn't allow form authentication for clients when access /oauth/token   
		.allowFormAuthenticationForClients()
		;
	}

	@Bean(name = "tokenStore")
	public TokenStore tokenStore() throws SQLException
	{
		// 需要使用 jdbc 的话，放开这里
//		 return new JdbcTokenStore((DataSource) BeanFactory.getBean("dataSourceMybatisMySql"));
		// 需要使用 redis 的话，放开这里
		return new RedisTokenStore(redisConnectionFactory);
	}
	
}
