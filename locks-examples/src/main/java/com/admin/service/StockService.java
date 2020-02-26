package com.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.dao.StockDao;
import com.admin.entity.Stock;

import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;
import chok.lock.redisson.RedissonLock;
import chok.lock.redisson.RedissonLockType;

@Service
public class StockService extends BaseService<Stock, Long>
{
	private final Logger log = LoggerFactory.getLogger(getClass());

//	// 锁的名字
//	private String key = "stock-lock-key-01";
//	// 尝试加锁的超时时间
//	private Long timeout = 1000L;
//	// 锁过期时间
//	private Long expire = 30L;
//
//	@Autowired
//	private RedissonClient redisson;

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
	 * 分布式锁避免库存超卖
	 * @param tid
	 * @param id
	 * @param qty
	 * @throws Exception
	 */
	@RedissonLock(lockKey = "stock-lock-key-02", tryLockOption = 3, lockType = RedissonLockType.REENTRANT_LOCK, lockFailMsg = "其他用户正在操作，请等待！")
	public void deductInventoryWithDistributedLock(int tid, long id, int qty) throws Exception
	{
		Stock stock = dao.get(id);
		log.info("线程[{}]，库存信息：{}", tid, stock.toString());
		// if (0 == stock.getQty() || 0 > stock.getQty() - qty)
		// {
		// throw new Exception("【分布式锁】库存不足！");
		// }
		// else
		// {
		stock.setQty(stock.getQty() - qty);
		dao.upd(stock);
		// }
		
		// 测试tryLock2 (arg参数需大于RedissonLock.waitTime)
//		Thread.sleep(2000);
		// 测试tryLock3 (arg参数需大于RedissonLock.leaseTime)
		Thread.sleep(2000);
		
	}
//	@RedissonLock(lockKey = "stock-lock-key-01", lockType = RedissonLockType.REENTRANT_LOCK, lockFailMsg = "其他用户正在操作，请等待！")
//	public void deductInventoryWithDistributedLock(int tid, long id, int qty) throws Exception
//	{
//		Stock stock = dao.get(id);
//		log.info("线程[{}]，库存信息：{}", tid, stock.toString());
//		// if (0 == stock.getQty() || 0 > stock.getQty() - qty)
//		// {
//		// throw new Exception("【分布式锁】库存不足！");
//		// }
//		// else
//		// {
//		stock.setQty(stock.getQty() - qty);
//		dao.upd(stock);
//		// }
//	}

//	public void deductInventoryWithDistributedLock(int tid, long id, int qty) throws Exception
//	{
//		// 定义锁
//		RLock lock = redisson.getLock(key);
//
//		if (lock.tryLock())
//		{
//			try
//			{
//				Stock stock = dao.get(id);
//				log.info("线程[{}]，锁名[{}]，加锁成功！库存信息：{}", tid, lock.getName(), stock.toString());
//				if (0 == stock.getQty() || 0 > stock.getQty() - qty)
//				{
//					throw new Exception("【分布式锁】库存不足！");
//				}
//				else
//				{
//					stock.setQty(stock.getQty() - qty);
//					dao.upd(stock);
//				}
//			}
//			finally
//			{
//				lock.unlock();
//				log.info("线程[{}]，锁名[{}]，释放锁!", tid, lock.getName());
//			}
//		}
//		else
//		{
//			log.info("线程[{}]，锁名[{}]，加锁失败！", tid, lock.getName());
//		}
//	}

	/**
	 * 利用悲观锁模拟库存超卖
	 * 
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
