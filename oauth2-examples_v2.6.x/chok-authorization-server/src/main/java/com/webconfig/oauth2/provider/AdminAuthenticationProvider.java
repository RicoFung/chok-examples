package com.webconfig.oauth2.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

//@Component
public class AdminAuthenticationProvider implements AuthenticationProvider
{
	static Logger log = LoggerFactory.getLogger(AdminAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String username = (String) authentication.getPrincipal(); 
		String password = (String) authentication.getCredentials();
		log.debug("username:{}, password:{}", username, password);
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
