package com.api.tbdemo1.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "AddDTO 新增入参")
public class TbDemo1AddDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "tcCode不能为空！")
	private java.lang.String tcCode;
	@NotBlank(message = "tcName不能为空！")
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

	@Override
	public String toString()
	{
		return "TbDemo [tcCode=" + tcCode + ", tcName=" + tcName + "]";
	}

}
