package com.admin.tbtask.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "TbTaskStartDTO 启动入参")
public class TbTaskStartDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键", example = "0", position = 1, required = true)
	@NotNull(message = "tcRowid不能为空！")
	private java.lang.Long tcRowid;

	public java.lang.Long getTcRowid()
	{
		return tcRowid;
	}

	public void setTcRowid(java.lang.Long tcRowid)
	{
		this.tcRowid = tcRowid;
	}
	
}

