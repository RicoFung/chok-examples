package com.api.tbdemo1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.tbdemo1.dao.TbDemo1Dao;
import com.api.tbdemo1.entity.TbDemo;

import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;

@Service
public class TbDemo1Service extends BaseService<TbDemo, Long>
{
	@Autowired
	private TbDemo1Dao dao;

	@Override
	public BaseDao<TbDemo, Long> getEntityDao() 
	{
		return dao;
	}
}
