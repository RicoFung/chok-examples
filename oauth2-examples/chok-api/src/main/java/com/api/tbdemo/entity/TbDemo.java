package com.api.tbdemo.entity;

import java.io.Serializable;
/**
 *
 * @author rico
 * @version 1.0
 * @since 1.0
 * */
public class TbDemo implements Serializable
{
	private static final long serialVersionUID = 1L;
	
    // tcRowid       db_column: TC_ROWID 
	private java.lang.Long tcRowid;
    // tcCode       db_column: TC_CODE 
	private java.lang.String tcCode;
    // tcName       db_column: TC_NAME 
	private java.lang.String tcName;

	public TbDemo(){
	}

	public TbDemo(
		java.lang.Long tcRowid,
		java.lang.String tcCode,
		java.lang.String tcName
	)
	{
		this.tcRowid = tcRowid;
		this.tcCode = tcCode;
		this.tcName = tcName;
	}

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

