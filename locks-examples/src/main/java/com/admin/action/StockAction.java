package com.admin.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.admin.service.StockService;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/admin/stock")
public class StockAction
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private StockService service;

	@RequestMapping(value = "/deductInventoryWithDistributedLock", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JSONObject deductInventoryWithDistributedLock(@RequestBody JSONObject jsonParam)
	{
		JSONObject restResult = new JSONObject();
		restResult.put("success", true);
		restResult.put("msg", "");

		int tid = jsonParam.getIntValue("tid");
		int id = jsonParam.getIntValue("id");
		int qty = jsonParam.getIntValue("qty");
		log.info("==> 线程[{}]，请求参数：{}", tid, jsonParam.toJSONString());

		try
		{
			service.deductInventoryWithDistributedLock(tid, id, qty);
		}
		catch (Exception e)
		{
			log.error("<== 线程[{}]，异常提示：{}", tid, e.getMessage());
			restResult.put("success", false);
			restResult.put("msg", e.getMessage());
		}

		return restResult;
	}

}
