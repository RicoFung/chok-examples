/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration(proxyBeanMethods = false)
public class SecurityConfig
{
	@Bean
	public CorsConfigurationSource corsConfigurationSource()
	{
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// 对所有url生效
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http.authorizeRequests((requests) -> requests
				// .antMatchers("/test/info", "/test/oidc",
				// "/test/hello").hasAnyAuthority("ROLE_ANONYMOUS")
				.anyRequest().authenticated());
		http.oauth2Login(Customizer.withDefaults());
		http.oauth2Client();

		// 显式指定某个已注册的oauth2.provider登录
		// http.oauth2Login(oauth2Login ->
		// oauth2Login.loginPage("/oauth2/authorization/rico-client-oidc"));
		// http.oauth2Client();
		return http.build();
	}
}
