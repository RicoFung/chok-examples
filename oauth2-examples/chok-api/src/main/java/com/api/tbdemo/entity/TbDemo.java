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
	
	private java.lang.String[] selectFields;
    // tcRowid       db_column: TC_ROWID 
	private java.lang.Long tcRowid;
    // tcCode       db_column: TC_CODE 
	private java.lang.String tcCode;
    // tcName       db_column: TC_NAME 
	private java.lang.String tcName;
	
	public java.lang.String[] getSelectFields()
	{
		return selectFields;
	}
	public void setSelectFields(java.lang.String[] selectFields)
	{
		this.selectFields = selectFields;
	}
	public java.lang.Long getTcRowid()
	{
		return tcRowid;
	}
	public void setTcRowid(java.lang.Long tcRowid)
	{
		this.tcRowid = tcRowid;
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

