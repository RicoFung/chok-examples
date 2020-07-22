package com.api.tbdemo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "GetDTO 明细入参")
public class TbDemoGetDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "动态列", example = "[\"tcRowid\",\"tcCode\"]", position = 0)
	private java.lang.String[] dynamicColumns;
	
	@ApiModelProperty(value = "主键", example = "0", position = 1, required = true)
	@NotNull(message = "tcRowid不能为空！")
	private java.lang.Long tcRowid;

	public java.lang.String[] getDynamicColumns()
	{
		return dynamicColumns;
	}

	public void setDynamicColumns(java.lang.String[] dynamicColumns)
	{
		this.dynamicColumns = dynamicColumns;
	}

	public java.lang.Long getTcRowid()
	{
		return tcRowid;
	}

	public void setTcRowid(java.lang.Long tcRowid)
	{
		this.tcRowid = tcRowid;
	}

}
