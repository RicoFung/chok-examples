package com.webconfig.oauth2.filter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.webconfig.oauth2.handler.AdminAuthenticationFailureHandler;
import com.webconfig.oauth2.handler.AdminAuthenticationSuccessHandler;
import com.webconfig.oauth2.manager.CusAuthenticationManager;

//@Component
public class MyAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter
{
	@Autowired
	private ClientDetailsService clientDetailsService;
	
	public MyAuthenticationProcessingFilter(CusAuthenticationManager authenticationManager,
			AdminAuthenticationSuccessHandler adminAuthenticationSuccessHandler,
			AdminAuthenticationFailureHandler adminAuthenticationFailureHandler)
	{
		super(new AntPathRequestMatcher("/oauth/token", "POST"));
		this.setAuthenticationManager(authenticationManager);
		this.setAuthenticationSuccessHandler(adminAuthenticationSuccessHandler);
		this.setAuthenticationFailureHandler(adminAuthenticationFailureHandler);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException
	{
		String clientId = request.getParameter("client_id");
        String clientSecret = request.getParameter("client_secret");
    
        clientId = Objects.isNull(clientId) ? "" : clientId.trim();
        clientSecret = Objects.isNull(clientSecret) ? "" : clientSecret;
    
		String[] clientDetails = null;
		clientDetails = new String[] { clientId, clientSecret };
		
		BaseClientDetails details = (BaseClientDetails) this.clientDetailsService
				.loadClientByClientId(clientDetails[0]);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details.getClientId(),
				details.getClientSecret(), details.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(token);

		token.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);
        
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
//            username, password);
//        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
//        return this.getAuthenticationManager().authenticate(authRequest);
        
//        if (request.getContentType() == null || !request.getContentType().contains("application/json")) {
//            throw new AuthenticationServiceException("请求头类型不支持: " + request.getContentType());
//        }
//
//        UsernamePasswordAuthenticationToken authRequest;
//        try {
//            MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);
//            // 将前端传递的数据转换成jsonBean数据格式
//            User user = JSONObject.parseObject(wrappedRequest.getBodyJsonStrByJson(wrappedRequest), User.class);
//            authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), null);
//            authRequest.setDetails(authenticationDetailsSource.buildDetails(wrappedRequest));
//        } catch (Exception e) {
//            throw new AuthenticationServiceException(e.getMessage());
//        }
//        return this.getAuthenticationManager().authenticate(authRequest);

	}

}
