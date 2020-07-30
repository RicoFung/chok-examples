package com.admin.tbtask.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.admin.tbtask.dto.TbTaskAddDTO;
import com.admin.tbtask.dto.TbTaskDelDTO;
import com.admin.tbtask.dto.TbTaskGetDTO;
import com.admin.tbtask.dto.TbTaskQueryDTO;
import com.admin.tbtask.dto.TbTaskStartDTO;
import com.admin.tbtask.dto.TbTaskStopDTO;
import com.admin.tbtask.dto.TbTaskUpdDTO;
import com.admin.tbtask.entity.TbTask;
import com.admin.tbtask.runnable.TaskInvoke;
import com.admin.tbtask.runnable.TaskRunnable;
import com.admin.tbtask.service.TbTaskService;
import com.fasterxml.jackson.core.type.TypeReference;

import chok.common.RestConstants;
import chok.common.RestResult;
import chok.devwork.springboot.BaseRestController;
import chok.util.TimeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "admin-TbTask")
@RestController(value = "adminTbTaskController")
@RequestMapping("/admin/tbtask")
public class TbTaskController extends BaseRestController<TbTask>
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	// 接受任务的返回结果集
	private Map<Long, ScheduledFuture<?>> futureMap  = new HashMap<Long, ScheduledFuture<?>>();
	
	@Autowired
	private ThreadPoolTaskScheduler	threadPoolTaskScheduler;

	// 实例化一个线程池任务调度类,可以使用自定义的ThreadPoolTaskScheduler
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler()
	{
		return new ThreadPoolTaskScheduler();
	}

	@Autowired
	private TbTaskService service;

	@ApiOperation("启动")
	@RequestMapping(value = "/start", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult start(TbTaskStartDTO tbTaskStartDTO, BindingResult validResult)
	{
		restResult = new RestResult();
		try
		{
			if (log.isDebugEnabled())
			{
				log.debug("==> requestDto：{}", restMapper.writeValueAsString(tbTaskStartDTO));
			}
			// 表单校验
			if (validResult.hasErrors()) 
			{
				restResult.setSuccess(false);
				restResult.setCode(RestConstants.ERROR_CODE1);
				restResult.setMsg(getValidMsgs(validResult));
				return restResult;
			}
			// 任务记录校验
			TbTask tbTask = service.get(tbTaskStartDTO.getTcRowid());
			if (tbTask == null)
			{
				restResult.setSuccess(false);
				restResult.setCode(RestConstants.ERROR_CODE1);
				restResult.setMsg("任务不存在！");
				return restResult;
			}
			
			//
			TaskRunnable taskRunnable = new TaskRunnable(new TaskInvoke()
			{
				@Override
				public void execute()
				{
					System.out.println("执行" + tbTask.getTcName() + "：" + TimeUtil.getCurrentMillTime());
				}
			});
			//
			Trigger taskTigger = new Trigger()
			{
				@Override
				public Date nextExecutionTime(TriggerContext triggerContext)
				{
					return new CronTrigger(tbTask.getTcCron()).nextExecutionTime(triggerContext);
				}
			};
			// 执行任务
			ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(taskRunnable, taskTigger);
			if (future != null)
			{
				futureMap.put(tbTask.getTcRowid(), future);
				restResult.setMsg("任务【" + tbTask.getTcName() + "】启动成功！");
			}
			else
			{
				restResult.setSuccess(false);
				restResult.setCode(RestConstants.ERROR_CODE1);
				restResult.setMsg("任务【" + tbTask.getTcName() + "】启动失败！");
			}
			//
			if (log.isDebugEnabled())
			{
				log.debug(restResult.getMsg());
			}
		}
		catch(Exception e)
		{
			log.error("<== Exception：{}", e);
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}

	@ApiOperation("停止")
	@RequestMapping(value = "/stop", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult stop(TbTaskStopDTO tbTaskStopDTO, BindingResult validResult)
	{
		restResult = new RestResult();
		try
		{
			if (log.isDebugEnabled())
			{
				log.debug("==> requestDto：{}", restMapper.writeValueAsString(tbTaskStopDTO));
			}
			// 表单校验
			if (validResult.hasErrors()) 
			{
				restResult.setSuccess(false);
				restResult.setCode(RestConstants.ERROR_CODE1);
				restResult.setMsg(getValidMsgs(validResult));
				return restResult;
			}
			// 任务记录校验
			TbTask tbTask = service.get(tbTaskStopDTO.getTcRowid());
			if (tbTask == null)
			{
				restResult.setSuccess(false);
				restResult.setCode(RestConstants.ERROR_CODE1);
				restResult.setMsg("任务不存在！");
				return restResult;
			}
			// 任務停止
			ScheduledFuture<?> future = futureMap.get(tbTaskStopDTO.getTcRowid());
			if (future != null) 
			{
				future.cancel(true);
				futureMap.remove(tbTaskStopDTO.getTcRowid());
				restResult.setMsg("任务【" + tbTask.getTcName() + "】停止成功！");
	        }
			else
			{
				restResult.setSuccess(false);
				restResult.setCode(RestConstants.ERROR_CODE1);
				restResult.setMsg("任务【" + tbTask.getTcName() + "】停止失败！");
			}
			//
			if (log.isDebugEnabled())
			{
				log.debug(restResult.getMsg());
			}
		}
		catch(Exception e)
		{
			log.error("<== Exception：{}", e);
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}
	
	@ApiOperation("新增")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult add(@RequestBody @Validated TbTaskAddDTO tbTaskAddDTO, BindingResult validResult)
	{
		restResult = new RestResult();
		try
		{
			if (log.isDebugEnabled())
			{
				log.debug("==> requestDto：{}", restMapper.writeValueAsString(tbTaskAddDTO));
			}
			if (validResult.hasErrors()) 
			{
				restResult.setSuccess(false);
				restResult.setCode(RestConstants.ERROR_CODE1);
				restResult.setMsg(getValidMsgs(validResult));
				return restResult;
			}
			TbTask tbTask = new TbTask();
			BeanUtils.copyProperties(tbTaskAddDTO, tbTask);
			service.add(tbTask);
		}
		catch(Exception e)
		{
			log.error("<== Exception：{}", e);
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}

	@ApiOperation("删除")
	@RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult del(@RequestBody @Validated TbTaskDelDTO tbTaskDelDTO, BindingResult validResult) 
	{
		restResult = new RestResult();
		try
		{
			if (log.isDebugEnabled())
			{
				log.debug("==> requestDto：{}", restMapper.writeValueAsString(tbTaskDelDTO));
			}
			if (validResult.hasErrors()) 
			{
				restResult.setSuccess(false);
				restResult.setCode(RestConstants.ERROR_CODE1);
				restResult.setMsg(getValidMsgs(validResult));
				return restResult;
			}
			service.del(tbTaskDelDTO.getTcRowids());
			restResult.setSuccess(true);
		}
		catch(Exception e)
		{
			log.error("<== Exception：{}", e);
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}

	@ApiOperation("修改")
	@RequestMapping(value = "/upd", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult upd(@RequestBody @Validated TbTaskUpdDTO tbTaskUpdDTO, BindingResult validResult) 
	{
		restResult = new RestResult();
		try
		{
			if (log.isDebugEnabled())
			{
				log.debug("==> requestDto：{}", restMapper.writeValueAsString(tbTaskUpdDTO));
			}
			if (validResult.hasErrors()) 
			{
				restResult.setSuccess(false);
				restResult.setCode(RestConstants.ERROR_CODE1);
				restResult.setMsg(getValidMsgs(validResult));
				return restResult;
			}
			TbTask tbTask = new TbTask();
			BeanUtils.copyProperties(tbTaskUpdDTO, tbTask);
			service.upd(tbTask);
		}
		catch(Exception e)
		{
			log.error("<== Exception：{}", e);
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}

	@ApiOperation("明细")
	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult get(@RequestBody @Validated TbTaskGetDTO tbTaskGetDTO, BindingResult validResult) 
	{
		restResult = new RestResult();
		try
		{
			if (log.isDebugEnabled())
			{
				log.debug("==> requestDto：{}", restMapper.writeValueAsString(tbTaskGetDTO));
			}
			if (validResult.hasErrors()) 
			{
				restResult.setSuccess(false);
				restResult.setCode(RestConstants.ERROR_CODE1);
				restResult.setMsg(getValidMsgs(validResult));
				return restResult;
			}
			Map<String, Object> param = restMapper.convertValue(tbTaskGetDTO,
					new TypeReference<Map<String, Object>>()
			{
			});
			restResult.put("row", service.getDynamic(param));
		}
		catch(Exception e)
		{
			log.error("<== Exception：{}", e);
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}
	
	@ApiOperation("列表")
	@RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult query(@RequestBody TbTaskQueryDTO tbTaskQueryDTO)
	{
		restResult = new RestResult();
		try
		{
			if (log.isDebugEnabled())
			{
				log.debug("==> requestDto：{}", restMapper.writeValueAsString(tbTaskQueryDTO));
			}
			Map<String, Object> param = restMapper.convertValue(tbTaskQueryDTO, new TypeReference<Map<String, Object>>(){});
	        restResult.put("total", service.getCount(param));
			restResult.put("rows", service.queryDynamic(param));
		}
		catch (Exception e)
		{
			log.error("<== Exception：{}", e);
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}
}
