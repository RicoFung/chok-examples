/*
 * 授权服务器元信息（issue 端点）
 * http://localhost:9000/.well-known/oauth-authorization-server
 * 
 * 参考：https://felord.blog.csdn.net/article/details/123551894
 */
package com.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration(proxyBeanMethods = false)
public class AuthServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.formLogin(Customizer.withDefaults()).build();
    }
    
    /**
     * 正确的注册方式
     * 认证方式：CLIENT_SECRET_BASIC
     * 注意：OPENID 不可单独使用
     * @return
     */
	@Bean
	public RegisteredClientRepository registeredClientRepository()
	{
		RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("rico-client")
				.clientSecret("{noop}123")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				// 回调地址名单，不在此列将被拒绝 而且只能使用IP或者域名  不能使用 localhost
				// client 为 springboot 的回调地址
				.redirectUri("http://127.0.0.1:8080/login/oauth2/code/rico-client-oidc")
				.redirectUri("http://127.0.0.1:8080/authorized")
    			// client 为 vue 的回调地址
    			.redirectUri("http://127.0.0.1:7090")
    			.redirectUri("http://10.12.77.103:7090")
    			// client 为外网服务的回调地址
    			.redirectUri("https://oidcdebugger.com/debug")
				.scope(OidcScopes.OPENID)
				.scope("test.read")
				.build();
		return new InMemoryRegisteredClientRepository(registeredClient);
	}
    
    /**
     * 认证方式: CLIENT_SECRET_BASIC
     * 测试地址: http://auth-server:9000/oauth2/authorize?client_id=rico-client&client_secret=123&response_type=code&scope=test.read&redirect_uri=https://www.baidu.com
     */
//    @Bean
//    public RegisteredClientRepository registeredClientRepository() {
//    	RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
//    			.clientId("rico-client")
//    			.clientSecret("{noop}123")
//    			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//    			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//    			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
////    			.redirectUri("https://www.baidu.com")
//    			.redirectUri("http://127.0.0.1:8080/test/info")
//    			.scope("test.read")
//    			.build();
//    	return new InMemoryRegisteredClientRepository(registeredClient);
//    }
    
    /**
     * 认证方式: CLIENT_SECRET_POST
     * 测试地址: http://auth-server:9000/oauth2/authorize?client_id=rico-client&client_secret=123&response_type=code&scope=test.read&redirect_uri=https://oidcdebugger.com/debug
     */
//    @Bean
//    public RegisteredClientRepository registeredClientRepository() {
//    	RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
//    			.clientId("rico-client")
//    			.clientSecret("{noop}123")
//    			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
//    			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//    			.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//    	        .redirectUri("http://127.0.0.1:8080/login/oauth2/code/rico-client-oidc")
//    	        .redirectUri("http://127.0.0.1:8080/authorized")
//    			.redirectUri("http://127.0.0.1:8080/test/oidc")
//    			.redirectUri("https://oidcdebugger.com/debug")
//    			.scope(OidcScopes.OPENID)
//    			.build();
//    	return new InMemoryRegisteredClientRepository(registeredClient);
//    }
    
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        RSAKey rsaKey = generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    private static RSAKey generateRsa() throws NoSuchAlgorithmException {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
          .privateKey(privateKey)
          .keyID(UUID.randomUUID().toString())
          .build();
    }

    private static KeyPair generateRsaKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }
    
    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
          .issuer("http://auth-server:9000")
          .build();
    }
}
