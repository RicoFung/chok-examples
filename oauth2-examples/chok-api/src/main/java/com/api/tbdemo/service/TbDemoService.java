package com.api.tbdemo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.tbdemo.dao.TbDemoDao;
import com.api.tbdemo.entity.TbDemo;

import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;

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
	
//	public List<TbDemo> queryOnSelectFields(Map<String, Object> param)
//	{
//		return dao.queryOnSelectFields(param);
//	}
//	
//	public List<Map<String, Object>> queryMapOnSelectFields(Map<String, Object> param)
//	{
//		return dao.queryMapOnSelectFields(param);
//	}
}
