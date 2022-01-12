package com.api.procdemo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.procdemo.dao.ProcDemoDao;
import com.api.procdemo.entity.ProcDemo;

import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;

@Service
public class ProcDemoService extends BaseService<ProcDemo,Long>
{
	@Autowired
	private ProcDemoDao dao;

	@Override
	public BaseDao<ProcDemo,Long> getEntityDao() 
	{
		return dao;
	}
	
	public Map<Object, Object> pQuery(Map<String, Object> inParam)
	{
		return dao.pQuery(inParam);
	}
}
