package com.api.v2.tbdemo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "TbDemoExpDTO 导出入参")
public class TbDemoExpDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	// ********************************************************************************************
	// 0.文件参数
	// ********************************************************************************************

	@ApiModelProperty(value = "文件名", example = "导出文件", position = 0)
	private java.lang.String showFilename;
	
	@ApiModelProperty(value = "标题名", example = "导出数据", position = 1)
	private java.lang.String showTitle;
	
	@ApiModelProperty(value = "列名", example = "[\"tcRowid\",\"tcCode\",\"tcName\"]", position = 2)
	private java.lang.String[] showColumns;
	
	@ApiModelProperty(value = "列别名", example = "[\"TcRowid\",\"TcCode\",\"TcName\"]", position = 3)
	private java.lang.String[] showAlias;
	
	public java.lang.String getShowFilename()
	{
		return showFilename;
	}

	public void setShowFilename(java.lang.String showFilename)
	{
		this.showFilename = showFilename;
	}

	public java.lang.String getShowTitle()
	{
		return showTitle;
	}

	public void setShowTitle(java.lang.String showTitle)
	{
		this.showTitle = showTitle;
	}

	public java.lang.String[] getShowColumns()
	{
		return showColumns;
	}

	public void setShowColumns(java.lang.String[] showColumns)
	{
		this.showColumns = showColumns;
	}

	public java.lang.String[] getShowAlias()
	{
		return showAlias;
	}
	
	public void setShowAlias(java.lang.String[] showAlias)
	{
		this.showAlias = showAlias;
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

