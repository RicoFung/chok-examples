package com.api.procdemo1.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.procdemo1.dao.ProcDemo1Dao;
import com.api.procdemo1.entity.ProcDemo1;

import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;

@Service
public class ProcDemo1Service extends BaseService<ProcDemo1,Long>
{
	@Autowired
	private ProcDemo1Dao dao;

	@Override
	public BaseDao<ProcDemo1,Long> getEntityDao() 
	{
		return dao;
	}
	
	public Map<Object, Object> pQuery(Map<String, Object> inParam)
	{
		return dao.pQuery(inParam);
	}
}
