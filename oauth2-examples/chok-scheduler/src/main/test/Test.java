import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Test
{
//	@Autowired
//	LoadBalancerClient	loadBalancerClient;
//	@Autowired
//	RestTemplate		restTemplate;
//
//	@org.junit.Test
//	public void todo()
//	{
//		System.out.println("hi");
//		ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-client");
//		String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/user/getByUsername";
//		System.out.println(url);
//		System.out.println(restTemplate.getForObject(url, String.class));
//	}

//	@Autowired
//	private DictDao dao;
//	@Autowired
//	private TbBiHelpUserDao uDao;
	
//	@org.junit.Test
//	public void testDictDao()
//	{
//		TbBiHelpUser u = uDao.getByUsername("biuser");
//		System.out.println(u.getTcName());
//	}
	
//	@org.junit.Test
//	public void testDictDao()
//	{
//		String account = "biuser";
//		Map<Object, Object> result = dao.pBiImpTypeQuery(account);
//		for (Entry<Object, Object> entry : result.entrySet()) {
//			
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//			
//		}
//	}
	
//	@org.junit.Test
//	public void testDictDao() throws Exception
//	{
//		String account = "biuser";
//		List<Map<String, String>> resultList = dao.pQueryBiImpType(account);
//		for (Map<String, String> result : resultList) {
//			
//			System.out.println(result.toString());
//			
//		}
//	}
}
