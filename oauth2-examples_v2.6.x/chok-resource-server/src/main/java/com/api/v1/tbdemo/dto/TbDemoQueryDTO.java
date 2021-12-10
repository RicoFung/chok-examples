package com.api.v1.tbdemo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "TbDemoQueryDTO 列表入参")
public class TbDemoQueryDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	// ********************************************************************************************
	// 1.默认参数
	// ********************************************************************************************

	@Schema(title = "动态列", example = "[\"tcRowid\",\"tcCode\",\"tcName\"]")//, position = 0)
	private java.lang.String[] dynamicColumns;
	
	@Schema(title = "动态排序", example = "[{\"sortName\":\"tcRowid\",\"sortOrder\":\"DESC\"}]")//, position = 1)
	private List<Map<String, Object>> dynamicOrder;

	@Schema(title = "页码", example = "1")//, position = 2)
	private int page;

	@Schema(title = "页大小", example = "10")//, position = 3)
	private int pagesize;

	public String[] getDynamicColumns()
	{
		return dynamicColumns;
	}
	
	public void setDynamicColumns(String[] dynamicColumns)
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

	// ********************************************************************************************
	// 2.表单参数
	// ********************************************************************************************
	
    // tcRowid       db_column: TC_ROWID 
	@Schema(title = "tcRowid", example = "\"\"")//, position = 4)
	private java.lang.Long tcRowid;
    // tcCode       db_column: TC_CODE 
	@Schema(title = "tcCode", example = "\"\"")//, position = 5)
	private java.lang.String tcCode;
    // tcName       db_column: TC_NAME 
	@Schema(title = "tcName", example = "\"\"")//, position = 6)
	private java.lang.String tcName;

	public void setTcRowid(java.lang.Long value) 
	{
		this.tcRowid = value;
	}
	
	public java.lang.Long getTcRowid() 
	{
		return this.tcRowid;
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
}

