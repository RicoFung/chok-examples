package com.webconfig.oauth2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import chok.common.RestResult;

@Component
@Aspect
public class MyOAuth2TokenAspect
{
	private static Logger log = LoggerFactory.getLogger(MyOAuth2TokenAspect.class);

	/**
	 *  @Around是可以改变controller返回值的
	 * @param pjp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
	public Object handle(ProceedingJoinPoint pjp)
	{
		RestResult result = new RestResult();
		Object proceed = null;
		try
		{
			proceed = pjp.proceed();
		}
		catch (Throwable e)
		{
			log.error("exception: {}", e);
			result.setCode("500");
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			result.put("exception", e);
		}
		if (proceed != null)
		{
			ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
			OAuth2AccessToken body = responseEntity.getBody();
			if (responseEntity.getStatusCode().is2xxSuccessful())
			{
				result.put("oauth2", body);
			}
			else
			{
				log.error("error:{}", responseEntity.getStatusCode().toString());
				result.setCode("400");
				result.setSuccess(false);
				result.setMsg(responseEntity.getStatusCode().toString());
			}
		}
		return ResponseEntity.status(200).body(result);
	}
}
