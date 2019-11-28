package com.admin.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import chok.devwork.BaseController;
import chok.util.CollectionUtil;
import com.admin.service.StockService;
import com.admin.entity.Stock;

@Scope("prototype")
@Controller
@RequestMapping("/stock")
public class StockApi extends BaseController<Stock>
{
	@Autowired
	private StockService service;
	
	@RequestMapping("/add")
	public void add(Stock po) 
	{
		try
		{
			service.add(po);
			printJson(result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			printJson(result);
		}
	}
	
	@RequestMapping("/del")
	public void del() 
	{
		try
		{
			service.del(CollectionUtil.strToLongArray(req.getString("tcRowid"), ","));
			result.setSuccess(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		printJson(result);
	}
	
	@RequestMapping("/upd")
	public void upd(Stock po) 
	{
		try
		{
			service.upd(po);
			printJson(result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			printJson(result);
		}
	}

	@RequestMapping("/get")
	public void get() 
	{
		try
		{
			result.put("po", service.get(req.getLong("tcRowid")));
			printJson(result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			printJson(result);
		}
	}
	
	@RequestMapping("/query")
	public void query()
	{
		try
		{
			Map<String, Object> m = req.getParameterValueMap(false, true);
			result.put("total",service.getCount(m));
			result.put("rows",service.query(req.getDynamicSortParameterValueMap(m)));
			printJson(result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			printJson(result);
		}
	}
}
