package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import chok.common.GlobalDefaultExceptionHandler;
import chok.lock.redisson.RedissonLockAspect;

// 默认（只能扫描project内的bean）
@SpringBootApplication
/**
 * 外部（需要扫描project外的bean）
 * 1. 处理全局异常
 * 2. 使用自定义注解@RedissonLock
 */
@Import({GlobalDefaultExceptionHandler.class, RedissonLockAspect.class})
// 使用自定义注解@RedissonLock时，需启用
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class Application
{
	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}
}