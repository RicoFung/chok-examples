package com.api.v2.tbdemo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "TbDemoImpDTO 导入入参")
public class TbDemoImpDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "参数1", required = true)
	@NotEmpty(message = "参数1不能为空！")
	private String param1;
	
	@ApiModelProperty(value = "参数2", required = true)
	@NotEmpty(message = "参数2不能为空！")
	private String param2;
	
	public String getParam1()
	{
		return param1;
	}

	public void setParam1(String param1)
	{
		this.param1 = param1;
	}

	public String getParam2()
	{
		return param2;
	}

	public void setParam2(String param2)
	{
		this.param2 = param2;
	}

}

