package com.util;

import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JwtUtil
{
	private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

	private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

	// 构造私有
	private JwtUtil()
	{
	}

	/**
	 * 获取原始token信息
	 * 
	 * @param authorizationHeader
	 *            授权头部信息
	 * @return
	 */
	public static String getRawToken(String authorizationHeader)
	{
		return authorizationHeader.substring(AUTHORIZATION_HEADER_PREFIX.length());
	}

	/**
	 * 获取授权头部信息
	 * 
	 * @param rawToken
	 *            token信息
	 * @return
	 */
	public static String getAuthorizationHeader(String rawToken)
	{
		return AUTHORIZATION_HEADER_PREFIX + rawToken;
	}

	/**
	 * 校验授权头部信息格式合法性
	 * 
	 * @param authorizationHeader
	 *            授权头部信息
	 * @return
	 */
	public static boolean validate(String authorizationHeader)
	{
		return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(AUTHORIZATION_HEADER_PREFIX);
	}

	/**
	 * 生成token, 只在用户登录成功以后调用
	 * 
	 * @param userId
	 *            用户id
	 * @param jwtParam
	 *            JWT加密所需信息
	 * @return
	 */
	public static String createToken(String userId, JwtParam jwtParam)
	{
		return createToken(userId, null, jwtParam);
	}

	/**
	 * 生成token, 只在用户登录成功以后调用
	 * 
	 * @param userId
	 *            用户id
	 * @param claim
	 *            声明
	 * @param jwtParam
	 *            JWT加密所需信息
	 * @return
	 */
	public static String createToken(String userId, Map<String, Object> claim, JwtParam jwtParam)
	{
		try
		{
			// 使用HS256加密算法
			SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

			long nowMillis = System.currentTimeMillis();
			Date now = new Date(nowMillis);

			// 生成签名密钥
			byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtParam.getBase64Secret());
			SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

			// 添加构成JWT的参数
			JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("typ", "JWT").claim(JwtConstant.USER_ID_KEY, userId)
					.addClaims(claim).setIssuer(jwtParam.getName()).setIssuedAt(now)
					.signWith(signatureAlgorithm, signingKey);

			// 添加token过期时间
			long TTLMillis = jwtParam.getJwtExpires()  * 1000;
//			long TTLMillis = jwtParam.getJwtExpires() * 60 * 1000;
			if (TTLMillis >= 0)
			{
				long expMillis = nowMillis + TTLMillis;
				Date exp = new Date(expMillis);
				jwtBuilder.setExpiration(exp).setNotBefore(now);
			}

			return jwtBuilder.compact();
		}
		catch (Exception e)
		{
			// TODO 这里自行抛出异常
			log.error("签名失败", e);
			return null;
		}
	}

	/**
	 * 解析token
	 * 
	 * @param authToken
	 *            授权头部信息
	 * @param base64Secret
	 *            base64加密密钥
	 * @return
	 * @throws Exception 
	 */
	public static Claims parseToken(String authToken, String base64Secret) throws Exception
	{
		try
		{
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
					.parseClaimsJws(authToken).getBody();
			return claims;
		}
		catch (SignatureException se)
		{
			String msg = "密钥不匹配"	;
			log.error(msg, se);
			throw new Exception(msg);
		}
		catch (ExpiredJwtException ejw)
		{
			String msg = "token过期";
			log.error(msg, ejw);
			throw new Exception(msg);
		}
		catch (Exception e)
		{
			String msg = "token解析异常";
			log.error(msg, e);
			throw new Exception(msg);
		}
	}
}
