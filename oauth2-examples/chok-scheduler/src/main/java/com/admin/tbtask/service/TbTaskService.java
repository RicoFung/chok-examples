package com.admin.tbtask.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.admin.tbtask.dao.TbTaskDao;
import com.admin.tbtask.entity.TbTask;
import com.admin.tbtask.runnable.TaskInvoke;
import com.admin.tbtask.runnable.TaskRunnable;

import chok.common.RestConstants;
import chok.common.RestResult;
import chok.devwork.springboot.BaseDao;
import chok.devwork.springboot.BaseService;
import chok.util.TimeUtil;

@Service(value = "adminTbTaskService")
public class TbTaskService extends BaseService<TbTask, Long>
{

	// 接受任务的返回结果集
	private Map<Long, ScheduledFuture<?>> futureMap  = new HashMap<Long, ScheduledFuture<?>>();
	
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private ThreadPoolTaskScheduler	threadPoolTaskScheduler;

	// 实例化一个线程池任务调度类，可以使用自定义的ThreadPoolTaskScheduler
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler()
	{
		return new ThreadPoolTaskScheduler();
	}
	
	@Autowired
	private TbTaskDao dao;

	@Override
	public BaseDao<TbTask, Long> getEntityDao() 
	{
		return dao;
	}
	
	public RestResult start(Long tcRowid) throws Exception
	{
		RestResult restResult = new RestResult();
		// DB校验任务是否存在
		final TbTask tbTask = dao.get(tcRowid);
		if (tbTask == null)
		{
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setSuccess(false);
			restResult.setMsg("任务不存在！");
			return restResult;
		}
		// 校验内存运行状态
		if (futureMap.containsKey(tcRowid))
		{
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setSuccess(false);
			restResult.setMsg("任务【" + tbTask.getTcName() + "】正在运行中，重新【启动】请先【停止】！");
			return restResult;
		}
		else
		{
			// 创建Runable
			TaskRunnable taskRunnable = new TaskRunnable(new TaskInvoke()
			{
				@Override
				public void execute()
				{
					System.out.println("执行" + tbTask.getTcName() + "：" + TimeUtil.getCurrentMillTime());
				}
			});
			// 创建Trigger
			Trigger taskTrigger = new Trigger()
			{
				@Override
				public Date nextExecutionTime(TriggerContext triggerContext)
				{
					return new CronTrigger(tbTask.getTcCron()).nextExecutionTime(triggerContext);
				}
			};
			// 执行任务
			ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(taskRunnable, taskTrigger);
			if (future != null)
			{
				futureMap.put(tbTask.getTcRowid(), future);
				
//				SetOperations<String, Object>  rSet = redisTemplate.opsForSet();
				
				restResult.setMsg("任务【" + tbTask.getTcName() + "】启动成功！");
			}
			else
			{
				restResult.setSuccess(false);
				restResult.setCode(RestConstants.ERROR_CODE1);
				restResult.setMsg("任务【" + tbTask.getTcName() + "】启动失败！");
			}
		}
		return restResult;
	}
	
	public RestResult stop(Long tcRowid) throws Exception
	{
		RestResult restResult = new RestResult();
		// DB校验任务是否存在
		TbTask tbTask = dao.get(tcRowid);
		if (tbTask == null)
		{
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setSuccess(false);
			restResult.setMsg("任务不存在！");
		}
		else
		{
			// 任务停止
			ScheduledFuture<?> future = futureMap.get(tcRowid);
			if (future != null) 
			{
				future.cancel(true);
				futureMap.remove(tcRowid);
				restResult.setMsg("任务【" + tbTask.getTcName() + "】停止成功！");
	        }
			else
			{
				restResult.setSuccess(false);
				restResult.setCode(RestConstants.ERROR_CODE1);
				restResult.setMsg("任务【" + tbTask.getTcName() + "】停止失败，任务没有被启动！");
			}
		}
		return restResult;
	}
}
