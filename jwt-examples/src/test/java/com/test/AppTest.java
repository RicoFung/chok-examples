package com.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.App;
import com.admin.service.StockService;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class AppTest extends TestCase
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisTemplate<String, String> template;

	@Autowired
	private StockService service;

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

}
