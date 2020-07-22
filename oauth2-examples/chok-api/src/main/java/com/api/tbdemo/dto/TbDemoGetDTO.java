package com.api.tbdemo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "GetDTO 明细入参")
public class TbDemoGetDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "tcRowid不能为空！")
	private java.lang.Long tcRowid;

	public void setTcRowid(java.lang.Long value)
	{
		this.tcRowid = value;
	}

	public java.lang.Long getTcRowid()
	{
		return this.tcRowid;
	}

	@Override
	public String toString()
	{
		return "TbDemo [tcRowid=" + tcRowid + "]";
	}
}
