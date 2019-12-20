package com.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import io.jsonwebtoken.Claims;

public class JwtInterceptor implements HandlerInterceptor
{

	private final Logger	log	= LoggerFactory.getLogger(getClass());
	@Autowired
	private JwtParam		jwtParam;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		JSONObject restResult = new JSONObject();
		restResult.put("success", true);
		restResult.put("msg", "");

		// 忽略带JwtIgnore注解的请求, 不做后续token认证校验
		if (handler instanceof HandlerMethod)
		{
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			JwtIgnore jwtIgnore = handlerMethod.getMethodAnnotation(JwtIgnore.class);
			if (jwtIgnore != null)
			{
				return true;
			}
		}

		if (HttpMethod.OPTIONS.equals(request.getMethod()))
		{
			response.setStatus(HttpServletResponse.SC_OK);
			return true;
		}

		final String authHeader = request.getHeader(JwtConstant.AUTH_HEADER_KEY);

		if (StringUtils.isEmpty(authHeader))
		{
			String msg = "===== 用户未登录, 请先登录 =====";
			restResult.put("msg", msg);
			restResult.put("success", false);
			sendJsonMessage(response, restResult);
			log.error("msg");
			return false;
		}

		// 校验头格式校验
		if (!JwtUtil.validate(authHeader))
		{
			String msg = "===== token格式异常 =====";
			restResult.put("msg", msg);
			restResult.put("success", false);
			sendJsonMessage(response, restResult);
			log.error("msg");
			return false;
		}

		// token解析
		final String authToken = JwtUtil.getRawToken(authHeader);
		
		Claims claims = null;
		try
		{
			claims = JwtUtil.parseToken(authToken, jwtParam.getBase64Secret());
		}
		catch (Exception e) 
		{
			restResult.put("msg", e.getMessage());
			restResult.put("success", false);
			sendJsonMessage(response, restResult);
			log.error("msg");
			return false;
		}

		// 传递所需信息
		request.setAttribute("CLAIMS", claims);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception
	{

	}

	private void sendJsonMessage(HttpServletResponse response, Object obj) throws Exception
	{
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteDateUseDateFormat));
		writer.close();
		response.flushBuffer();
	}
}