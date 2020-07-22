package com.api.tbdemo1.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.tbdemo1.dto.TbDemo1AddDTO;
import com.api.tbdemo1.dto.TbDemo1DelDTO;
import com.api.tbdemo1.dto.TbDemo1GetDTO;
import com.api.tbdemo1.dto.TbDemo1QueryDTO;
import com.api.tbdemo1.dto.TbDemo1UpdDTO;
import com.api.tbdemo1.entity.TbDemo;
import com.api.tbdemo1.service.TbDemo1Service;
import com.fasterxml.jackson.core.type.TypeReference;

import chok.common.RestConstants;
import chok.common.RestResult;
import chok.devwork.springboot.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "DEMO-管理1")
@RestController
@RequestMapping("/api/tbdemo1")
public class TbDemo1Controller extends BaseRestController<TbDemo>
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private TbDemo1Service service;

	@ApiOperation("新增")
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult add(@RequestBody @Validated TbDemo1AddDTO tbDemo1AddDTO, BindingResult validResult)
	{
		if (log.isDebugEnabled())
		{
			log.debug("==> 请求参数：{}", tbDemo1AddDTO.toString());
		}
		if (validResult.hasErrors())
		{
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(getValidMsgs(validResult));
			return restResult;
		}
		try
		{
			TbDemo tbDemo = new TbDemo();
			BeanUtils.copyProperties(tbDemo1AddDTO, tbDemo);
			service.add(tbDemo);
		}
		catch (Exception e)
		{
			log.error("<== 异常提示：{}", e);
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}

	@ApiOperation("删除")
	@RequestMapping(value = "/del", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult del(@RequestBody @Validated TbDemo1DelDTO tbDemo1DelDTO, BindingResult validResult)
	{
		if (log.isDebugEnabled())
		{
			log.debug("==> 请求参数：{}", tbDemo1DelDTO.getTcRowids().toString());
		}
		if (validResult.hasErrors())
		{
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(getValidMsgs(validResult));
			return restResult;
		}
		try
		{
			service.del(tbDemo1DelDTO.getTcRowids());
			restResult.setSuccess(true);
		}
		catch (Exception e)
		{
			log.error("<== 异常提示：{}", e);
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}

	@ApiOperation("修改")
	@RequestMapping(value = "/upd", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult upd(@RequestBody @Validated TbDemo1UpdDTO tbDemo1UpdDTO, BindingResult validResult)
	{
		if (log.isDebugEnabled())
		{
			log.debug("==> 请求参数：{}", tbDemo1UpdDTO.toString());
		}
		if (validResult.hasErrors())
		{
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(getValidMsgs(validResult));
			return restResult;
		}
		try
		{
			TbDemo tbDemo = new TbDemo();
			BeanUtils.copyProperties(tbDemo1UpdDTO, tbDemo);
			service.upd(tbDemo);
		}
		catch (Exception e)
		{
			log.error("<== 异常提示：{}", e);
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}

	@ApiOperation("明细")
	@RequestMapping(value = "/get", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult get(@RequestBody @Validated TbDemo1GetDTO tbDemo1GetDTO, BindingResult validResult)
	{
		if (log.isDebugEnabled())
		{
			log.debug("==> 请求参数：{}", tbDemo1GetDTO.getTcRowid());
		}
		if (validResult.hasErrors())
		{
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(getValidMsgs(validResult));
			return restResult;
		}
		try
		{
			restResult.put("vo", service.get(tbDemo1GetDTO.getTcRowid()));
		}
		catch (Exception e)
		{
			log.error("<== 异常提示：{}", e);
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}

	@ApiOperation("列表")
	@RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult query(@RequestBody TbDemo1QueryDTO tbDemo1QueryDTO)
	{
		if (log.isDebugEnabled())
		{
			log.debug("==> 请求参数：{}", tbDemo1QueryDTO.toString());
		}
		try
		{
			Map<String, Object> params = restMapper.convertValue(tbDemo1QueryDTO,
					new TypeReference<Map<String, Object>>()
					{
					});
			restResult.put("total", service.getCount(params));
			restResult.put("rows", service.query(params));
		}
		catch (Exception e)
		{
			log.error("<== 异常提示：{}", e);
			restResult.setSuccess(false);
			restResult.setCode(RestConstants.ERROR_CODE1);
			restResult.setMsg(e.getMessage());
		}
		return restResult;
	}
}
