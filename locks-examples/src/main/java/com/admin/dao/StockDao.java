package com.admin.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.admin.entity.Stock;

import chok.devwork.springboot.BaseDao;

@Repository
public class StockDao extends BaseDao<Stock,Long>
{
	@Resource//(name = "firstSqlSessionTemplate")
	private SqlSession sqlSession;

	@Override
	protected SqlSession getSqlSession()
	{
		return sqlSession;
	}
	
	@Override
	public Class<Stock> getEntityClass()
	{
		return Stock.class;
	}

	/**
	 * 乐观锁处理库存超卖（跨应用无效）
	 * @param id
	 * @param qty
	 * @param version_new
	 * @param version_old
	 */
	public int deductInventoryWithOptimisticLock (long id, int qty, int version_new, int version_old)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("qty", qty);
		params.put("version_new", version_new);
		params.put("version_old", version_old);
		return upd("deductInventoryWithOptimisticLock", params);
	}
	
	/**
	 * 悲观锁获取库存对象
	 * @param id
	 * @return
	 */
	public Stock getWithPessimisticLock(long id)
	{
		return (Stock) get("getWithPessimisticLock", id);
	}
}
