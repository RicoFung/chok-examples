package com.api.procdemo.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "QueryDTO 列表入参")
public class ProcDemoQueryDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private java.lang.String tcCode;
	private java.lang.String tcName;

	@Schema(title = "页码", example = "1", required = true)
	private int page;

	@Schema(title = "页大小", example = "10", required = true)
	private int pagesize;

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
		return "TbDemoQueryDTO [tcCode=" + tcCode + ", tcName=" + tcName + ", page=" + page
				+ ", pagesize=" + pagesize + "]";
	}
}
