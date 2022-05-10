package com.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController
{

	@GetMapping("/info")
	public Map<String, Object> info(
			@RegisteredOAuth2AuthorizedClient("rico-client-authorization-code") OAuth2AuthorizedClient client)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> map = new HashMap<>();
		map.put("authentication", authentication);
		map.put("oAuth2AuthorizedClient", client);
		return map;
	}
	
	@GetMapping("/oidc")
	public Map<String, Object> oidc(
			@RegisteredOAuth2AuthorizedClient("rico-client-oidc") OAuth2AuthorizedClient client)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> map = new HashMap<>();
		map.put("authentication", authentication);
		map.put("oAuth2AuthorizedClient", client);
		return map;
	}
	
	@GetMapping("/hi")
	public String hi()
	{
		return "hi [client-server] & [resource-server]";
	}

}
