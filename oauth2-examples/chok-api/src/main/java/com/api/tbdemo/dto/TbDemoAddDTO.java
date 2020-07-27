package com.api.tbdemo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "TbDemoAddDTO 新增入参")
public class TbDemoAddDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

    // tcCode       db_column: TC_CODE 
	@ApiModelProperty(value = "tcCode", example = "\"\"")
	@NotNull(message = "tcCode不能为空！")
	private java.lang.String tcCode;
    // tcName       db_column: TC_NAME 
	@ApiModelProperty(value = "tcName", example = "\"\"")
	@NotNull(message = "tcName不能为空！")
	private java.lang.String tcName;

	public void setTcCode(java.lang.String value) 
	{
		this.tcCode = value;
	}
	
	public java.lang.String getTcCode() 
	{
		return this.tcCode;
	}
	public void setTcName(java.lang.String value) 
	{
		this.tcName = value;
	}
	
	public java.lang.String getTcName() 
	{
		return this.tcName;
	}
}

