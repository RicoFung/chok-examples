package com.webconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sso.service.TbUserInfo0aService;

import chok.util.EncryptionUtil;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	static Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	@Autowired
	private TbUserInfo0aService service;

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
		.csrf().disable().cors() // 禁用csrf，开启跨域
		.and()
		.authorizeRequests()
		.antMatchers("/oauth/*", "/login", "/logout", "/account/logout", "/revoke", "/error").permitAll()
		;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		// 内存认证
//		auth.inMemoryAuthentication()
//		.withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
//		.and()
//		.withUser("user1").password(passwordEncoder().encode("user1")).roles("USER");
		
		// 自定义认证（兼容项目需要，原则上不再允许使用MD5）
		auth.userDetailsService(service).passwordEncoder(new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword)
			{
				return epoMD5(rawPassword);
			}
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword)
			{
				String epoPassword = epoMD5(rawPassword);
				return epoPassword.equals(encodedPassword);
			}
		});
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	/**
	 * EPO MD5 
	 * 兼容旧DRP逻辑，最多只取前8位进行加密
	 * @param rawPassword
	 * @return
	 */
	private String epoMD5(CharSequence rawPassword)
	{
		String pwd1 = (String) rawPassword;
		String pwd2 = pwd1.length() <= 8 ? pwd1 : pwd1.substring(0, 8);
		String epoPassword = EncryptionUtil.getMD5(pwd2);
		return epoPassword;
	}
	
}