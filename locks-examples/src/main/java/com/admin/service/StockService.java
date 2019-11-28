package com.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;
import com.admin.dao.StockDao;
import com.admin.entity.Stock;

@Service
public class StockService extends BaseService<Stock, Long>
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private StockDao dao;

	@Override
	public BaseDao<Stock, Long> getEntityDao()
	{
		return dao;
	}

	/**
	 * 乐观锁避免库存超卖（跨应用无效）
	 * 
	 * @param tid
	 * @param id
	 * @param qty
	 * @throws Exception
	 */
	public void deductInventoryWithOptimisticLock(int tid, long id, int qty) throws Exception
	{
		Stock stock = dao.get(id);
		log.info("线程[{}]，{}", tid, stock.toString());
		int qtyNew = stock.getQty() - qty;
		int versionOld = stock.getVersion();
		int versionNew = versionOld + 1;
		int updateRows = dao.deductInventoryWithOptimisticLock(id, qtyNew, versionNew, versionOld);
		if (0 == updateRows)
		{
			throw new Exception("【乐观锁】库存不足！");
		}
	}

	/**
	 * 悲观锁避免库存超卖（并发性能差）
	 * 
	 * @param tid
	 * @param id
	 * @param qty
	 * @throws Exception
	 */
	public void deductInventoryWithPessimisticLock(int tid, long id, int qty) throws Exception
	{
		Stock stock = dao.getWithPessimisticLock(id);
		log.info("线程[{}]，{}", tid, stock.toString());
		if (0 == stock.getQty() || 0 > stock.getQty() - qty)
		{
			throw new Exception("【悲观锁】库存不足！");
		}
		else
		{
			stock.setQty(stock.getQty() - qty);
			dao.upd(stock);
		}
	}

	/**
	 * 利用悲观锁模拟库存超卖
	 * @param tid
	 * @param id
	 * @param qty
	 * @throws Exception
	 */
	public void deductInventoryOversold(int tid, long id, int qty) throws Exception
	{
		Stock stock = dao.getWithPessimisticLock(id);
		log.info("线程[{}]，{}", tid, stock.toString());
		stock.setQty(stock.getQty() - qty);
		dao.upd(stock);
	}
}
