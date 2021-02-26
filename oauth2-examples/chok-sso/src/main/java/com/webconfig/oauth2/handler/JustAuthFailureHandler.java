package com.webconfig.oauth2.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import chok.common.RestResult;

@Component
public class JustAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler
{
	private static Logger log = LoggerFactory.getLogger(JustAuthFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException
	{
		RestResult result = new RestResult();

		if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException)
		{
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		else if (e instanceof LockedException)
		{
			result.setSuccess(false);
			result.setMsg("账户被锁定，请联系管理员!");
		}
		else if (e instanceof CredentialsExpiredException)
		{
			result.setSuccess(false);
			result.setMsg("证书过期，请联系管理员!");
		}
		else if (e instanceof AccountExpiredException)
		{
			result.setSuccess(false);
			result.setMsg("账户过期，请联系管理员!");
		}
		else if (e instanceof DisabledException)
		{
			result.setSuccess(false);
			result.setMsg("账户被禁用，请联系管理员!");
		}
		else
		{
			result.setSuccess(false);
			log.error("登录失败：", e);
			result.setMsg("登录失败!");
		}
		printJson(response, result);
	}

	public void printJson(HttpServletResponse response, Object result) throws JsonProcessingException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		// 返回数据
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(402);
		try
		{
			objectMapper.writeValue(response.getOutputStream(), result);
		}
		catch (Exception e)
		{
			log.error(objectMapper.writeValueAsString(e));
		}
		finally
		{
			log.error(objectMapper.writeValueAsString(result));
		}
	}
}
