package com.webconfig.oauth2.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

//@Component
public class MyBasicAuthenticationFilter extends OncePerRequestFilter
{
	@Autowired
	private ClientDetailsService clientDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException
	{

//			if (!request.getRequestURI().equals("/oauth/token") || !request.getParameter("grant_type").equals("password"))
		if (!request.getServletPath().equals("/oauth/token") || !request.getParameter("grant_type").equals("password"))
		{
			filterChain.doFilter(request, response);
			return;
		}

		String[] clientDetails = this.isHasClientDetails(request);

		if (clientDetails == null)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("error", HttpStatus.UNAUTHORIZED.value());
			map.put("message", "请求中未包含客户端信息");
			map.put("path", request.getServletPath());
			map.put("timestamp", String.valueOf(new Date().getTime()));
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			try
			{
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(response.getOutputStream(), map);
			}
			catch (Exception e)
			{
				throw new ServletException();
			}
			return;
		}

		this.handle(request, response, clientDetails, filterChain);

	}

	private void handle(HttpServletRequest request, HttpServletResponse response, String[] clientDetails,
			FilterChain filterChain) throws IOException, ServletException
	{
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//		if (authentication != null && authentication.isAuthenticated())
//		{
//			filterChain.doFilter(request, response);
//			return;
//		}

		BaseClientDetails details = (BaseClientDetails) this.clientDetailsService
				.loadClientByClientId(clientDetails[0]);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details.getClientId(),
				details.getClientSecret(), details.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(token);

		filterChain.doFilter(request, response);
	}

	// 判断请求头中是否包含client信息，不包含返回false
	private String[] isHasClientDetails(HttpServletRequest request)
	{

		String[] params = null;

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (header != null)
		{

			String basic = header.substring(0, 5);

			if (basic.toLowerCase().contains("basic"))
			{

				String tmp = header.substring(6);
				String defaultClientDetails = new String(Base64.getDecoder().decode(tmp));

				String[] clientArrays = defaultClientDetails.split(":");

				if (clientArrays.length != 2)
				{
					return params;
				}
				else
				{
					params = clientArrays;
				}

			}
		}

		String id = request.getParameter("client_id");
		String secret = request.getParameter("client_secret");

		if (header == null && id != null)
		{
			params = new String[] { id, secret };
		}

		return params;
	}

	public ClientDetailsService getClientDetailsService()
	{
		return clientDetailsService;
	}

	public void setClientDetailsService(ClientDetailsService clientDetailsService)
	{
		this.clientDetailsService = clientDetailsService;
	}

}
