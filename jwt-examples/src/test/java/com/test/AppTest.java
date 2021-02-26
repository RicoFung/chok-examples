package com.test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.App;
import com.admin.service.StockService;

/**
 * Unit test for simple App.
 */
@SpringBootTest(classes = App.class)
public class AppTest
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisTemplate<String, String> template;

	@Autowired
	private StockService service;

	@Test
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
