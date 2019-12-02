package com.admin.action;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.admin.service.StockService;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/admin/stock")
public class StockAction
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedissonClient redisson;

	@Autowired
	private StockService service;
	
	// 锁的名字
	String key = "stock-lock-key-01";
	// 尝试加锁的超时时间
	Long timeout = 1000L;
	// 锁过期时间
	Long expire = 30L;
	
	@RequestMapping(value="/deductInventoryWithDistributedLock", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JSONObject deductInventoryWithDistributedLock(@RequestBody JSONObject jsonParam) 
	{
		JSONObject restResult = new JSONObject();
		restResult.put("success", true);
		restResult.put("msg", "");
		
		log.info("==> 请求参数：{}", jsonParam.toJSONString());
		int tid = jsonParam.getIntValue("tid");
		int id = jsonParam.getIntValue("id");
		int qty = jsonParam.getIntValue("qty");
		
		// 定义锁
		RLock lock = redisson.getLock(key);
		try
		{
			// 获取锁
			if (lock.tryLock(timeout, expire, TimeUnit.MILLISECONDS))
			{
				try
				{
					service.deductInventoryWithDistributedLock(tid, id, qty);
				}
				catch (Exception e)
				{
					log.error("<== 异常提示：{}", e.getMessage());
					restResult.put("success", false);
					restResult.put("msg", e.getMessage());
				}
			}
			else
			{
			}
		}
		catch (InterruptedException e)
		{
			log.error("尝试获取分布式锁失败: {}", e);
		}
		finally
		{
			// 释放锁
			try
			{
				lock.unlock();
			}
			catch (Exception e)
			{
				// do nothing
			}
		}	

		return restResult;
	}

}
