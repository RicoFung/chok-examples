package com.admin.entity;

import java.io.Serializable;
/**
 *
 * @author rico
 * @version 1.0
 * @since 1.0
 * */
public class Stock implements Serializable
{
	private static final long serialVersionUID = 1L;
	
    // id       db_column: id 
	private java.lang.Integer id;
    // sku       db_column: sku 
	private java.lang.String sku;
    // qty       db_column: qty 
	private java.lang.Integer qty;
	// version       db_column: version 
	private java.lang.Integer version;

	public Stock(){
	}

	public Stock(
		java.lang.Integer id,
		java.lang.String sku,
		java.lang.Integer qty,
		java.lang.Integer version
	)
	{
		this.id = id;
		this.sku = sku;
		this.qty = qty;
		this.version = version;
	}

	public void setId(java.lang.Integer value) 
	{
		this.id = value;
	}
	
	public java.lang.Integer getId() 
	{
		return this.id;
	}
	public void setSku(java.lang.String value) 
	{
		this.sku = value;
	}
	
	public java.lang.String getSku() 
	{
		return this.sku;
	}
	public void setQty(java.lang.Integer value) 
	{
		this.qty = value;
	}
	
	public java.lang.Integer getQty() 
	{
		return this.qty;
	}

	public java.lang.Integer getVersion()
	{
		return version;
	}

	public void setVersion(java.lang.Integer version)
	{
		this.version = version;
	}

	@Override
	public String toString()
	{
		return "Stock [id=" + id + ", sku=" + sku + ", qty=" + qty + ", version=" + version + "]";
	}
}

