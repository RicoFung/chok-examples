package com.api.procdemo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.procdemo.dto.ProcDemoQueryDTO;
import com.api.procdemo.entity.ProcDemo;
import com.api.procdemo.service.ProcDemoService;
import com.fasterxml.jackson.core.type.TypeReference;

import chok.common.RestConstants;
import chok.common.RestResult;
import chok.devwork.springboot.BaseRestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "DEMO-存储过程")
@RestController
@RequestMapping("/api/procdemo")
public class ProcDemoController extends BaseRestController<ProcDemo>
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProcDemoService service;

	@ApiOperation("列表")
	@RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public RestResult query(@RequestBody ProcDemoQueryDTO procDemoQueryDTO)
	{
		if (log.isDebugEnabled())
		{
			log.debug("==> 请求参数：{}", procDemoQueryDTO.toString());
		}
		try
		{
			Map<String, Object> params = restMapper.convertValue(procDemoQueryDTO,
					new TypeReference<Map<String, Object>>()
					{
					});
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
