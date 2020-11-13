package com.oauth;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.v2.tbdemo.dto.TbDemoQueryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xkcoding.justauth.AuthRequestFactory;

import chok.common.RestConstants;
import chok.common.RestResult;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import me.zhyd.oauth.config.AuthDefaultSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;

/**
 * <p>
 * 第三方登录 Controller
 * </p>
 *
 * @package: com.xkcoding.oauth.controller
 * @description: 第三方登录 Controller
 * @author: yangkai.shen
 * @date: Created in 2019-05-17 10:07
 * @copyright: Copyright (c) 2019
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RestController
@RequestMapping("/oauth")
//@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class OauthController
{
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AuthRequestFactory factory;
//	private final AuthRequestFactory factory;

	/**
	 * 登录类型
	 */
	@GetMapping
	public Map<String, String> loginType()
	{
		List<String> oauthList = factory.oauthList();
		return oauthList.stream().collect(Collectors.toMap(oauth -> oauth.toLowerCase() + "登录",
				oauth -> "http://localhost:6161/chok-api/oauth/login/" + oauth.toLowerCase()));
	}

	@RequestMapping(value = "/test/{param}")
	public String test(@PathVariable String param)
	{
		return param;
	}
	
	/**
	 * 登录
	 *
	 * @param oauthType
	 *            第三方登录类型
	 * @param response
	 *            response
	 * @throws IOException
	 */
	@RequestMapping("/login/{oauthType}")
	public void renderAuth(@PathVariable String oauthType, HttpServletResponse response) throws IOException
	{
		AuthRequest authRequest = factory.get(getAuthSource(oauthType));
		response.sendRedirect(authRequest.authorize(oauthType + "::" + AuthStateUtils.createState()));
	}

	/**
	 * 登录成功后的回调
	 *
	 * @param oauthType
	 *            第三方登录类型
	 * @param callback
	 *            携带返回的信息
	 * @return 登录成功后的信息
	 * @throws JsonProcessingException 
	 */
	@RequestMapping("/{oauthType}/callback")
	public AuthResponse<?> login(@PathVariable String oauthType, AuthCallback callback) throws JsonProcessingException
	{
		AuthRequest authRequest = factory.get(getAuthSource(oauthType));
		AuthResponse<?> response = authRequest.login(callback);
		
		ObjectMapper om = new ObjectMapper();
		
		log.info("【response】= {}", om.writeValueAsString(response));
//		log.info("【response】= {}", JSONUtil.toJsonStr(response));
		return response;
	}

	private String getAuthSource(String type)
	{
		if (StrUtil.isNotBlank(type))
		{
			return AuthDefaultSource.valueOf(type.toUpperCase()).toString();
		}
		else
		{
			throw new RuntimeException("不支持的类型");
		}
	}
}