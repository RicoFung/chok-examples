package com.api.tbdemo.entity;

import java.io.Serializable;

import org.springframework.util.ObjectUtils;

import io.netty.util.internal.StringUtil;
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
	
	public java.lang.Long getTcRowid()
	{
		return ObjectUtils.isEmpty(tcRowid) ? 0l : tcRowid;
	}
	public void setTcRowid(java.lang.Long tcRowid)
	{
		this.tcRowid = tcRowid;
	}
	public java.lang.String getTcCode()
	{
		return StringUtil.isNullOrEmpty(tcCode) ? "" : tcCode;
	}
	public void setTcCode(java.lang.String tcCode)
	{
		this.tcCode = tcCode;
	}
	public java.lang.String getTcName()
	{
		return StringUtil.isNullOrEmpty(tcName) ? "" : tcName;
	}
	public void setTcName(java.lang.String tcName)
	{
		this.tcName = tcName;
	}

}

