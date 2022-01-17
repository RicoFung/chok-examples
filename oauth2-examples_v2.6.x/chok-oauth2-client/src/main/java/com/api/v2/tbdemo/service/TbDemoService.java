package com.api.v2.tbdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.v2.dao.TbDemoDao;
import com.api.v2.entity.TbDemo;

import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;
import chok.lock.redisson.RedissonLock;
import chok.lock.redisson.RedissonLockType;


@Service(value = "v2TbDemoService")
public class TbDemoService extends BaseService<TbDemo,Long>
{
	@Autowired
	private TbDemoDao dao;

	@Override
	public BaseDao<TbDemo,Long> getEntityDao() 
	{
		return dao;
	}
	

	@Override
	public int add(TbDemo demo) throws Exception
	{
		dao.add(demo);
		throw new Exception("Error !!!");
	}
	
	@RedissonLock(lockKey = "test-lock-key-01", tryLockOption = 1, lockType = RedissonLockType.REENTRANT_LOCK, lockFailMsg = "其他用户正在操作，请等待！")
	public int addWithRedissonLock(TbDemo demo) throws Exception
	{
		dao.add(demo);
		return 0;
	}
}
