import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Application;

import chok.util.EncryptionUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest(classes = Application.class)
public class AppTest
{
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
//	@Autowired
//	private Md5PasswordEncoder md5PasswordEncoder;
	
	private Md5Crypt md5 = new Md5Crypt();

	@Test
	public void todo()
	{
//		System.out.println(new MessageDigestPasswordEncoder("MD5").encode("045718"));
		System.out.println(EncryptionUtil.getMD5("54PROGRAMMER."));
//		System.out.println(passwordEncoder.encode("QjD3L75JRO"));
//		System.out.println(passwordEncoder.encode("HGUAJRM649"));
//		System.out.println(passwordEncoder.encode("123456"));
	}

//	@Test
	// 测试时需MARK掉 AuthServerConfig类的oauthServer.allowFormAuthenticationForClients();
	public void givenDBUser_whenRevokeToken_thenAuthorized()
	{
		String accessToken = obtainAccessToken("client1", "rico", "123");
		System.out.println(accessToken);
//		assertNotNull(accessToken);
	}

	private String obtainAccessToken(String clientId, String username, String password)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "password");
		params.put("client_id", clientId);
		params.put("username", username);
		params.put("password", password);
		Response response = RestAssured.given().auth().preemptive().basic(clientId, "secret").and().with()
				.params(params).when().post("http://localhost:9090/chok-sso/oauth/token");
		return response.jsonPath().getString("access_token");
	}
}
