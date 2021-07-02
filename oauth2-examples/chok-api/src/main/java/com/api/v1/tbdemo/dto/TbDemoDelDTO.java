package com.api.v1.tbdemo.dto;

import java.io.Serializable;
import java.util.Arrays;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "TbDemoDelDTO 删除入参")
public class TbDemoDelDTO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "tcRowids不能为空！")
    @Size(min = 1, message = "最少选一条记录！")
	private Long[] tcRowids;
	
	public Long[] getTcRowids()
	{
		return tcRowids;
	}
	public void setTcRowids(Long[] tcRowids)
	{
		this.tcRowids = tcRowids;
	}
	
	@Override
	public String toString()
	{
		return "TbDemoDelDTO [tcRowids=" + Arrays.toString(tcRowids) + "]";
	}
}
