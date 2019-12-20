package com.admin.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.util.JwtIgnore;
import com.util.JwtParam;
import com.util.JwtUtil;

@RestController
@RequestMapping("/admin/authentication")
public class LoginAction
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private JwtParam jwtParam;

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @JwtIgnore // 加此注解, 请求不做token验证
	public JSONObject login(@RequestBody JSONObject jsonParam)
	{
		JSONObject restResult = new JSONObject();
		restResult.put("success", true);
		restResult.put("msg", "");

		String username = jsonParam.getString("username");
		String password = jsonParam.getString("password");
		log.info("==> 请求参数：{}", jsonParam.toJSONString());

		try
		{
			// 1.校验用户密码
			// 省略

			// 2.验证通过生成token
			String token = JwtUtil.createToken(username + "", jwtParam);
			if (token == null)
			{
				String msg = "===== 用户签名失败 =====";
				restResult.put("success", false);
				restResult.put("msg", msg);
				log.error(msg);
			}
			else
			{
				String msg = "===== 用户{}生成签名{} =====";
				restResult.put("token", JwtUtil.getAuthorizationHeader(token));
				log.info(msg, username, token);
			}
		}
		catch (Exception e)
		{
			restResult.put("success", false);
			restResult.put("msg", e.getMessage());
			log.error("<== 异常提示：{}", e.getMessage());
		}

		return restResult;
	}

}
