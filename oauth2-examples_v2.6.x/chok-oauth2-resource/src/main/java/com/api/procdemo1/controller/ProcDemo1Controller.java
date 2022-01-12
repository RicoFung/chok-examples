package com.api.procdemo1.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.procdemo1.dto.ProcDemo1QueryDTO;
import com.api.procdemo1.entity.ProcDemo1;
import com.api.procdemo1.service.ProcDemo1Service;
import com.fasterxml.jackson.core.type.TypeReference;

import chok.common.RestConstants;
import chok.common.RestResult;
import chok.devwork.springboot.BaseRestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "DEMO-存储过程1")
@RestController
@RequestMapping("/api/procdemo1")
public class ProcDemo1Controller extends BaseRestController<ProcDemo1>
{
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ProcDemo1Service service;

	@Operation(summary = "列表")
	@RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult query(@RequestBody ProcDemo1QueryDTO procDemoQueryDTO)
	{
		if (log.isDebugEnabled())
		{
			log.debug("==> 请求参数：{}", procDemoQueryDTO.toString());
		}
		try
		{
			Map<String, Object> params = restMapper.convertValue(procDemoQueryDTO, new TypeReference<Map<String, Object>>(){});
	        restResult.getData().putAll(service.pQuery(params));
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
