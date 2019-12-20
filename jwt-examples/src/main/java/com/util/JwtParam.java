package com.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "jwt")
public class JwtParam
{

	/**
	 * 发行者名
	 */
    @Value("${jwt.name}")
	private String name;

	/**
	 * base64加密密钥
	 */
    @Value("${jwt.base64-secret}")
	private String base64Secret;

	/**
	 * jwt中过期时间设置(分)
	 */
    @Value("${jwt.jwt-expires}")
	private int jwtExpires;

	public String getName()
	{
		return name;
	}

	public String getBase64Secret()
	{
		return base64Secret;
	}

	public int getJwtExpires()
	{
		return jwtExpires;
	}

}
