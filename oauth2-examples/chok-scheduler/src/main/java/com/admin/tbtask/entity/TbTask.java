package com.admin.tbtask.entity;

import java.io.Serializable;
/**
 *
 * @author rico
 * @version 1.0
 * @since 1.0
 * */
public class TbTask implements Serializable
{
	private static final long serialVersionUID = 1L;
	
    // tcRowid       db_column: TC_ROWID 
	private java.lang.Long tcRowid;
    // tcCode       db_column: TC_CODE 
	private java.lang.String tcCode;
    // tcName       db_column: TC_NAME 
	private java.lang.String tcName;
    // tcCron       db_column: TC_CRON 
	private java.lang.String tcCron;
    // tcStatus       db_column: TC_STATUS 
	private java.lang.String tcStatus;

	public TbTask(){
	}

	public TbTask(
		java.lang.Long tcRowid,
		java.lang.String tcCode,
		java.lang.String tcName,
		java.lang.String tcCron,
		java.lang.String tcStatus
	)
	{
		this.tcRowid = tcRowid;
		this.tcCode = tcCode;
		this.tcName = tcName;
		this.tcCron = tcCron;
		this.tcStatus = tcStatus;
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
	public void setTcCron(java.lang.String value) 
	{
		this.tcCron = value;
	}
	
	public java.lang.String getTcCron() 
	{
		return this.tcCron;
	}
	public void setTcStatus(java.lang.String value) 
	{
		this.tcStatus = value;
	}
	
	public java.lang.String getTcStatus() 
	{
		return this.tcStatus;
	}
}

