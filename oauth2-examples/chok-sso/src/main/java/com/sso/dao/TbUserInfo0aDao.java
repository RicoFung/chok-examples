package com.sso.dao;

import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.sso.entity.TbUserInfo0a;

import chok.devwork.springboot.BaseDao;

@Repository
public class TbUserInfo0aDao extends BaseDao<TbUserInfo0a,Long>
{
	@Resource(name = "sqlSessionTemplateMybatisOracle")
	private SqlSession sqlSession;

	@Override
	protected SqlSession getSqlSession()
	{
		return sqlSession;
	}
	
	@Override
	public Class<TbUserInfo0a> getEntityClass()
	{
		return TbUserInfo0a.class;
	}
	
	public TbUserInfo0a getByUsername(String username)
	{
		return (TbUserInfo0a) get("getByUsername", username);
	}
}
