package com.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.App;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class AppTest extends TestCase
{
	@Autowired
	private RedissonClient redisson;

	@Autowired
	private RedisTemplate<String, String> template;

	// @org.junit.Test
	public void testApp()
	{
		// redisson.getKeys().flushall();

		// RMap<String, String> m = redisson.getMap("test1", StringCodec.INSTANCE);
		// m.put("1", "12");

		BoundHashOperations<String, String, String> hash = template.boundHashOps("test1");
		String t = hash.get("1");
		System.out.println("[TEST1] <= " + t.equals("82"));
	}

	@org.junit.Test
	public void testLock()
	{

		// 锁的名字
		String key = "myTest002";
		// 尝试加锁的超时时间
		Long timeout = 1000L;
		// 锁过期时间
		Long expire = 60000L;

		// 定义锁
		RLock lock = redisson.getLock(key);
		try
		{
			lock.tryLock(timeout, expire, TimeUnit.MILLISECONDS);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// 关闭连接
		redisson.shutdown();
	}

	public void testRedLock() throws InterruptedException
	{
		// 锁的名字
		String key = "myTest001";
		// 尝试加锁的超时时间
		Long timeout = 1000L;
		// 锁过期时间
		Long expire = 30L;
		// 并发数
		Integer size = 100;

		// 定义线程池
		ExecutorService executorService = Executors.newFixedThreadPool(size);

		// 定义倒计时门闩：以保证所有线程执行完毕再进行最后的计数
		CountDownLatch latchCount = new CountDownLatch(size);

		// 计数器
		LongAdder adderSuccess = new LongAdder();
		LongAdder adderFail = new LongAdder();

		// 多线程执行
		for (int i = 0; i < size; i++)
		{
			executorService.execute(() ->
			{
				// 定义锁
				RLock lock = redisson.getLock(key);
				try
				{
					// 获取锁
					if (lock.tryLock(timeout, expire, TimeUnit.MILLISECONDS))
					{
						// 成功计数器累加1
						adderSuccess.increment();
						latchCount.countDown();
					}
					else
					{
						// 失败计数器累加1
						adderFail.increment();
						latchCount.countDown();
					}
				}
				catch (InterruptedException e)
				{
					System.out.println("尝试获取分布式锁失败");
					// log.error("尝试获取分布式锁失败", e);
				}
				finally
				{
					// 释放锁
					try
					{
						// lock.unlock();
					}
					catch (Exception e)
					{
						// do nothing
					}
				}
			});
		}
		// 等待所有线程执行完毕
		latchCount.await();

		// 关闭线程池
		executorService.shutdown();

		// 关闭连接
		redisson.shutdown();

		System.out.println("共计「" + adderSuccess.intValue() + "」获取锁成功，「" + adderFail.intValue() + "」获取锁失败。");
		// log.info("共计「{}」获取锁成功，「{}」获取锁失败。", adderSuccess.intValue(),
		// adderFail.intValue());
	}
}
