package com.webconfig;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import com.admin.tbtask.dao.TbTaskDao;

@Configuration
public class TaskConfig implements SchedulingConfigurer 
{
	@Autowired
	private TbTaskDao dao;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar)
	{
//        taskRegistrar.addTriggerTask(
//                //1.添加任务内容(Runnable)
//                () -> System.out.println("执行定时任务2: " + LocalDateTime.now().toLocalTime()),
//                //2.设置执行周期(Trigger)
//                triggerContext -> {
//                    //2.1 从数据库获取执行周期
//                    String cron = cronMapper.getCron();
//                    //2.2 合法性校验.
//                    if (StringUtils.isEmpty(cron)) {
//                        // Omitted Code ..
//                    }
//                    //2.3 返回执行周期(Date)
//                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
//                }
//        );
	}

}
