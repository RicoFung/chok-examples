package com.webconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


//@Configuration
@EnableWebSecurity
public class WebSecurityConfig //extends WebSecurityConfigurerAdapter
{
	static Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	// @formatter:off
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception
	{
		http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated()).formLogin();
		return http.build();
	}
	// @formatter:on

	/**
	 * Users user details service.
	 *
	 * @return the user details service
	 */
	// @formatter:off
	@Bean
	UserDetailsService users()
	{
		UserDetails user = User.builder().username("rico").password("123")
				.passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode).roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
	// @formatter:on

	/**
	 * Web security customizer web security customizer.
	 *
	 * @return the web security customizer
	 */
	@Bean
	WebSecurityCustomizer webSecurityCustomizer()
	{
		return web -> web.ignoring().antMatchers("/oauth2/token", "/oauth2/authorize", "/oauth2/*");
	}
	
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //对默认的UserDetailsService进行覆盖
        authenticationProvider.setUserDetailsService(users());
        return authenticationProvider;
    }
	
//	@Autowired
//	private TbUserInfo0aService service;
//
//	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception
//	{
//		return super.authenticationManagerBean();
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception
//	{
//		http
//		.csrf().disable().cors() // 禁用csrf，开启跨域
//		.and()
//		.authorizeRequests()
//		.antMatchers("/oauth/*", "/login", "/logout", "/account/logout", "/revoke", "/error").permitAll()
//		;
//	}
//	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception
//	{
//		// 内存认证
////		auth.inMemoryAuthentication()
////		.withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
////		.and()
////		.withUser("user1").password(passwordEncoder().encode("user1")).roles("USER");
//		
//		// 自定义认证（兼容项目需要，原则上不再允许使用MD5）
//		auth.userDetailsService(service).passwordEncoder(new PasswordEncoder() {
//			@Override
//			public String encode(CharSequence rawPassword)
//			{
//				return epoMD5(rawPassword);
//			}
//			@Override
//			public boolean matches(CharSequence rawPassword, String encodedPassword)
//			{
//				String epoPassword = epoMD5(rawPassword);
//				return epoPassword.equals(encodedPassword);
//			}
//		});
//	}
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder()
//	{
//		return new BCryptPasswordEncoder();
//	}
//
//	/**
//	 * EPO MD5 
//	 * 兼容旧DRP逻辑，最多只取前8位进行加密
//	 * @param rawPassword
//	 * @return
//	 */
//	private String epoMD5(CharSequence rawPassword)
//	{
//		String pwd1 = (String) rawPassword;
//		String pwd2 = pwd1.length() <= 8 ? pwd1 : pwd1.substring(0, 8);
//		String epoPassword = EncryptionUtil.getMD5(pwd2);
//		return epoPassword;
//	}
	
}