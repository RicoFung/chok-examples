package com.webconfig.oauth2.handler;

import java.io.IOException;
import java.io.PrintWriter;

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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import chok.common.RestResult;

@Component
public class AdminAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler//implements AuthenticationFailureHandler
{
	private static Logger log = LoggerFactory.getLogger(AdminAuthenticationFailureHandler.class);
	
	private PrintWriter out;

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

	public void printJson(HttpServletResponse response, Object o)
	{
		response.setContentType("application/json;charset=UTF-8");
		try
		{
			if(out == null)
			{
				out = response.getWriter();
			}
			String s = JSON.toJSONString(o);
			if (log.isDebugEnabled()) log.debug("Response JSON <== {}", s);
			out.print(s);
		}
		catch(Exception ex)
		{
			log.error(ex.getMessage());
		}
		finally 
		{
			out.close();
		}
	}
}
