package com.api.tbdemo.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "QueryDTO 列表入参")
public class TbDemoQueryDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "可选列", example = "")
	private java.lang.String[] selectFields;
	@ApiModelProperty(value = "代号", example = "\"\"")
	private java.lang.String tcCode;
	@ApiModelProperty(value = "名称", example = "\"\"")
	private java.lang.String tcName;

	@ApiModelProperty(value = "页码", example = "1", required = true)
	private int page;

	@ApiModelProperty(value = "页大小", example = "10", required = true)
	private int pagesize;

	public java.lang.String[] getSelectFields()
	{
		return selectFields;
	}

	public void setSelectFields(java.lang.String[] selectFields)
	{
		this.selectFields = selectFields;
	}

	public java.lang.String getTcCode()
	{
		return tcCode;
	}

	public void setTcCode(java.lang.String tcCode)
	{
		this.tcCode = tcCode;
	}

	public java.lang.String getTcName()
	{
		return tcName;
	}

	public void setTcName(java.lang.String tcName)
	{
		this.tcName = tcName;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getPagesize()
	{
		return pagesize;
	}

	public void setPagesize(int pagesize)
	{
		this.pagesize = pagesize;
	}

}
