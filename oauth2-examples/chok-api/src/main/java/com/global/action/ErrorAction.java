package com.global.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import chok.devwork.BaseController;

@Scope("prototype")
@Controller
@RequestMapping("/error")
public class ErrorAction extends BaseController<Object>
{
	@RequestMapping("/401")
	public String error401() 
	{
		return "jsp/error/401";
	}
	
	@RequestMapping("/403")
	public String error403() 
	{
		return "jsp/error/403";
	}
}