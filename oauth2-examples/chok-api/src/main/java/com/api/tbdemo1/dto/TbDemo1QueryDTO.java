package com.api.tbdemo1.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "QueryDTO1 列表入参")
public class TbDemo1QueryDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private java.lang.String tcRowid;
	private java.lang.String tcCode;
	private java.lang.String tcName;

	@ApiModelProperty(value = "页码", example = "1", required = true)
	private int page;

	@ApiModelProperty(value = "页大小", example = "10", required = true)
	private int pagesize;

	
	public java.lang.String getTcRowid()
	{
		return tcRowid;
	}

	public void setTcRowid(java.lang.String tcRowid)
	{
		this.tcRowid = tcRowid;
	}

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

	@Override
	public String toString()
	{
		return "QueryDTO [tcRowid=" + tcRowid + ", tcCode=" + tcCode + ", tcName=" + tcName + ", page=" + page
				+ ", pagesize=" + pagesize + "]";
	}

}
