package com.api.tbdemo1.dao;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.api.tbdemo1.entity.TbDemo;

import chok.devwork.springboot.BaseDao;

@Repository
public class TbDemo1Dao extends BaseDao<TbDemo, Long>
{
	@Resource//(name = "firstSqlSessionTemplate")
	private SqlSession sqlSession;

	@Override
	protected SqlSession getSqlSession()
	{
		return sqlSession;
	}
	
	@Override
	public Class<TbDemo> getEntityClass()
	{
		return TbDemo.class;
	}
}
