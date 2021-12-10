package com.webconfig.oauth2.manager;

import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.webconfig.oauth2.provider.AdminAuthenticationProvider;

//@Component
public class CusAuthenticationManager implements AuthenticationManager
{
	private final AdminAuthenticationProvider adminAuthenticationProvider;

	public CusAuthenticationManager(AdminAuthenticationProvider adminAuthenticationProvider)
	{
		this.adminAuthenticationProvider = adminAuthenticationProvider;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		Authentication result = adminAuthenticationProvider.authenticate(authentication);
		if (Objects.nonNull(result))
		{
			return result;
		}
		throw new ProviderNotFoundException("Authentication failed!");
	}

}
