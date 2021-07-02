package com.webconfig.oauth2.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import chok.common.RestResult;

//@Component
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
	private static Logger log = LoggerFactory.getLogger(AdminAuthenticationFailureHandler.class);
	
	private PrintWriter out;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException
	{
		RestResult result = new RestResult();
		result.put("authentication", authentication);
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
