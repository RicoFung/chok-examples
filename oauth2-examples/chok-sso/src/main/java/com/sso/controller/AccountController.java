package com.sso.controller;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chok.common.RestResult;
import chok.devwork.springboot.BaseRestController;

@RestController
@RequestMapping("/sso/account")
public class AccountController extends BaseRestController<Object>
{
	static Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private TokenStore tokenStore;

	@RequestMapping("/me")
	public Principal me(Principal principal)
	{
		log.info(principal+"");
		return principal;
	}
	
	@PostMapping("/logout")
	public RestResult logout(@RequestHeader("Authorization") String authorization)
	{
		String access_token = authorization.substring(7);
		try
		{
			if (StringUtils.isNotBlank(access_token))
			{
				OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(access_token);
				if (oAuth2AccessToken != null)
				{
					if (log.isDebugEnabled())
					{
						log.debug("==> access_token: {}", oAuth2AccessToken.getValue());
					}
					tokenStore.removeAccessToken(oAuth2AccessToken);
					OAuth2RefreshToken oAuth2RefreshToken = oAuth2AccessToken.getRefreshToken();
					tokenStore.removeRefreshToken(oAuth2RefreshToken);
					tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
				}
				else
				{
					restResult.setSuccess(false);
					restResult.setMsg("access_token 无效！");
				}
			}
		}
		catch (Exception e)
		{
			log.error("<== 异常提示：{}", e);
			restResult.setSuccess(false);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}
}
