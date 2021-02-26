package com.webconfig.oauth2.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import chok.common.RestResult;

@Component
public class JustAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
	private static Logger log = LoggerFactory.getLogger(JustAuthFailureHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException
	{
		RestResult result = new RestResult();
		result.put("authentication", authentication);
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
