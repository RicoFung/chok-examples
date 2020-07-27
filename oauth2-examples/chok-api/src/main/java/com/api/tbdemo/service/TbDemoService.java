package com.api.tbdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;
import com.api.tbdemo.dao.TbDemoDao;
import com.api.tbdemo.entity.TbDemo;

@Service
public class TbDemoService extends BaseService<TbDemo, Long>
{
	@Autowired
	private TbDemoDao dao;

	@Override
	public BaseDao<TbDemo, Long> getEntityDao() 
	{
		return dao;
	}
}
