package com.api.v2.tbdemo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "TbDemoAddDTO 新增入参")
public class TbDemoAddDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

    // tcCode       db_column: TC_CODE 
	@Schema(title = "tcCode", example = "\"\"")//, position = 0)
	@NotNull(message = "tcCode不能为空！")
	private java.lang.String tcCode;
    // tcName       db_column: TC_NAME 
	@Schema(title = "tcName", example = "\"\"")//, position = 1)
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

