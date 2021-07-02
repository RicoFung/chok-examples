package com.sso.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chok.devwork.springboot.BaseRestController;

@RestController
@RequestMapping("/sso/test")
public class TestController extends BaseRestController<Object>
{
	static Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired  
    private HttpServletRequest request;  
    
	@Autowired
	private TokenStore tokenStore;

	@PostMapping("/product/{id}")
	public String getProduct(@PathVariable String id)
	{
		// for debug
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "product id : " + id;
	}

	@PostMapping("/order/{id}")
	public String getOrder(@PathVariable String id)
	{
		// for debug
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "order id : " + id;
	}
}
