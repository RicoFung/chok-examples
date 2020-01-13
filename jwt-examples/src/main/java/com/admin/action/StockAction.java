package com.admin.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/admin/stock")
public class StockAction
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public JSONObject test(@RequestBody JSONObject jsonParam)
	{
		JSONObject restResult = new JSONObject();
		restResult.put("success", true);
		restResult.put("msg", "");

		try
		{
			String v = jsonParam.getString("v");
			restResult.put("result", v);
			log.info("==> 请求参数：{}", jsonParam.toJSONString());
		}
		catch (Exception e)
		{
			log.error("<== 异常提示：{}", e.getMessage());
			restResult.put("success", false);
			restResult.put("msg", e.getMessage());
		}

		return restResult;
	}

}
