package com.api.v2.tbdemo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "TbDemoExpRptDTO 导出入参")
public class TbDemoExpRptDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	// ********************************************************************************************
	// 0.文件参数
	// ********************************************************************************************
	@ApiModelProperty(value = "文件名", example = "rpt", position = 0)
	private java.lang.String rptName;
	
	@ApiModelProperty(value = "文件格式:pdf/xlsx/html", example = "\"pdf\"", position = 1)
	@NotNull(message = "文件格式“rptFormat”不能为空！")
	private String rptFormat;

	public java.lang.String getRptName()
	{
		return rptName;
	}

	public void setRptName(java.lang.String rptName)
	{
		this.rptName = rptName;
	}

	public String getRptFormat()
	{
		return rptFormat;
	}

	public void setRptFormat(String rptFormat)
	{
		this.rptFormat = rptFormat;
	}

	// ********************************************************************************************
	// 1.默认参数
	// ********************************************************************************************
	@ApiModelProperty(value = "动态列", example = "[\"tcRowid\",\"tcCode\",\"tcName\"]", position = 4)
	private java.lang.String[] dynamicColumns;
	
	@ApiModelProperty(value = "动态排序", example = "[{\"sortName\":\"tcRowid\",\"sortOrder\":\"DESC\"}]", position = 5)
	private List<Map<String, Object>> dynamicOrder;

	@ApiModelProperty(value = "主键数组（用于导出被勾选的行）", example = "[]", position = 6)
	private Long[] tcRowids;

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
	
	public Long[] getTcRowids()
	{
		return tcRowids;
	}
	
	public void setTcRowids(Long[] tcRowids)
	{
		this.tcRowids = tcRowids;
	}
	
	// ********************************************************************************************
	// 2.表单参数
	// ********************************************************************************************
	
    // tcRowid       db_column: TC_ROWID 
	@ApiModelProperty(value = "tcRowid", example = "\"\"", position = 7)
	private java.lang.Long tcRowid;
    // tcCode       db_column: TC_CODE 
	@ApiModelProperty(value = "tcCode", example = "\"\"", position = 8)
	private java.lang.String tcCode;
    // tcName       db_column: TC_NAME 
	@ApiModelProperty(value = "tcName", example = "\"\"", position = 9)
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
