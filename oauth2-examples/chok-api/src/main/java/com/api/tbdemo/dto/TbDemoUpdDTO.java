package com.api.tbdemo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "TbDemoUpdDTO 修改入参")
public class TbDemoUpdDTO implements Serializable
{
	private static final long serialVersionUID = 1L;

    // tcRowid       db_column: TC_ROWID 
	@ApiModelProperty(value = "tcRowid", example = "\"\"")
	@NotNull(message = "tcRowid不能为空！")
	private java.lang.Long tcRowid;
    // tcCode       db_column: TC_CODE 
	@ApiModelProperty(value = "tcCode", example = "\"\"")
	@NotNull(message = "tcCode不能为空！")
	private java.lang.String tcCode;
    // tcName       db_column: TC_NAME 
	@ApiModelProperty(value = "tcName", example = "\"\"")
	@NotNull(message = "tcName不能为空！")
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

