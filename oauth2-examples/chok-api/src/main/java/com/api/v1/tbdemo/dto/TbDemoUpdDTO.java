package com.api.v1.tbdemo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "TbDemoUpdDTO 修改入参")
public class TbDemoUpdDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

	// tcRowid       db_column: TC_ROWID 
	@ApiModelProperty(value = "tcRowid", example = "\"\"", position = 0)
	@NotNull(message = "tcRowid(tcRowid) 不能为空！")
	private java.lang.Long tcRowid;
	
    // tcCode       db_column: TC_CODE 
	@ApiModelProperty(value = "tcCode", example = "\"\"", position = 1)
	//	@NotNull(message = "tcCode(tcCode) 不能为空！")
	private java.lang.String tcCode;
    // tcName       db_column: TC_NAME 
	@ApiModelProperty(value = "tcName", example = "\"\"", position = 2)
	//	@NotNull(message = "tcName(tcName) 不能为空！")
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

