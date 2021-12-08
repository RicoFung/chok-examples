package com.webconfig;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncThreadPoolConfig
{
    @Value("${async_thread_pool.core_pool_size}")
    private Integer ASYNC_THREAD_POOL_CORE_POOL_SIZE;
    @Value("${async_thread_pool.max_pool_size}")
    private Integer ASYNC_THREAD_MAX_CORE_POOL_SIZE;
    @Value("${async_thread_pool.queue_capacity}")
    private Integer ASYNC_THREAD_POOL_QUEUE_CAPACITY;
    @Value("${async_thread_pool.keep_alive_seconds}")
    private Integer ASYNC_THREAD_POOL_KEEP_ALIVE_SECONDS;
    @Value("${async_thread_pool.await_termination_seconds}")
    private Integer ASYNC_THREAD_POOL_AWAIT_TERMINATION_SECONDS;
    @Value("${async_thread_pool.thread_name_prefix}")
    private String ASYNC_THREAD_POOL_THREAD_NAME_PREFIX;
    
	// 设置Bean的名称不设置的话没有办法在 任务中对应 配置信息
	@Bean("asyncTaskExecutor")
	public Executor asyncTaskExecutor()
	{
		// 根据ThreadPoolTaskExecutor 创建建线程池
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 为线程设置初始的线程数量 5条线程
		executor.setCorePoolSize(ASYNC_THREAD_POOL_CORE_POOL_SIZE);
		// 为线程设置最大的线程数量 10条线程
		executor.setMaxPoolSize(ASYNC_THREAD_MAX_CORE_POOL_SIZE);
		// 为任务队列设置最大 任务数量
		executor.setQueueCapacity(ASYNC_THREAD_POOL_QUEUE_CAPACITY);
		// 设置超出初始化线程的 存在时间为60秒
		// 也就是 如果现有线程数超过5 则会对超出的空闲线程 设置摧毁时间 也就是60秒
		executor.setKeepAliveSeconds(ASYNC_THREAD_POOL_KEEP_ALIVE_SECONDS);
		// 线程池的饱和策略 我这里设置的是 CallerRunsPolicy 也就是由用调用者所在的线程来执行任务 共有四种
		// AbortPolicy：直接抛出异常，这是默认策略；
		// CallerRunsPolicy：用调用者所在的线程来执行任务；
		// DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
		// DiscardPolicy：直接丢弃任务；
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		// 设置在关闭线程池时是否等待任务完成
		executor.setWaitForTasksToCompleteOnShutdown(true);
		// 设置等待终止的秒数
		executor.setAwaitTerminationSeconds(ASYNC_THREAD_POOL_AWAIT_TERMINATION_SECONDS);
		// 设置线程前缀
		executor.setThreadNamePrefix(ASYNC_THREAD_POOL_THREAD_NAME_PREFIX);
		// 返回设置完成的线程池
		return executor;
	}
}
