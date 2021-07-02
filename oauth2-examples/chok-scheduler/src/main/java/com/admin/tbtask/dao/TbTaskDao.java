package com.admin.tbtask.dao;

import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import chok.devwork.springboot.BaseDao;
import com.admin.tbtask.entity.TbTask;

@Repository(value = "adminTbTaskDao")
public class TbTaskDao extends BaseDao<TbTask, Long>
{
	@Resource//(name = "firstSqlSessionTemplate")
	private SqlSession sqlSession;

	@Override
	protected SqlSession getSqlSession()
	{
		return sqlSession;
	}
	
	@Override
	public Class<TbTask> getEntityClass()
	{
		return TbTask.class;
	}
}
