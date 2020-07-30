package com.admin.tbtask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.tbtask.dao.TbTaskDao;
import com.admin.tbtask.entity.TbTask;

import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;

@Service(value = "adminTbTaskService")
public class TbTaskService extends BaseService<TbTask, Long>
{
	@Autowired
	private TbTaskDao dao;

	@Override
	public BaseDao<TbTask, Long> getEntityDao() 
	{
		return dao;
	}
	
	public void start(Long tcRowid) throws Exception
	{
		TbTask tbTask = dao.get(tcRowid);
		if (tbTask == null)
		{
			throw new Exception("任务不存在！");
		}
		else
		{
			
		}
	}
}
