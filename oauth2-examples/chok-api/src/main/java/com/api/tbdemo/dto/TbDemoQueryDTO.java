package com.api.tbdemo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "QueryDTO 列表入参")
public class TbDemoQueryDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "动态列", example = "[\"tcRowid\",\"tcCode\"]", position = 0)
	private java.lang.String[] dynamicColumns;
	
	@ApiModelProperty(value = "动态排序", example = "[{\"sortName\":\"tcRowid\",\"sortOrder\":\"DESC\"},{\"sortName\":\"tcCode\",\"sortOrder\":\"ASC\"}]", position = 1)
	private List<Map<String, Object>> dynamicOrder;
	
	@ApiModelProperty(value = "页码", example = "1", required = true, position = 2)
	private int page;
	
	@ApiModelProperty(value = "页大小", example = "10", required = true, position = 3)
	private int pagesize;
	
	@ApiModelProperty(value = "代号", example = "\"\"", position = 4)
	private java.lang.String tcCode;
	@ApiModelProperty(value = "名称", example = "\"\"", position = 5)
	private java.lang.String tcName;
	
	public java.lang.String[] getDynamicColumns()
	{
		return dynamicColumns;
	}
	public void setDynamicColumns(java.lang.String[] dynamicColumns)
	{
		this.dynamicColumns = dynamicColumns;
	}
	public List<Map<String, Object>> getDynamicOrder()
	{
		return dynamicOrder;
	}
	public void setDynamicOrder(List<Map<String, Object>> dynamicOrder)
	{
		this.dynamicOrder = dynamicOrder;
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
	
}
