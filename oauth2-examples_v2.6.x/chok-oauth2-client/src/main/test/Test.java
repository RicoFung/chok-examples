import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Application;
import com.api.v2.entity.TbDemo;
import com.api.v2.tbdemo.service.TbDemoService;

import chok.common.RestResult;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Test
{
	@Autowired
	TbDemoService service;
	
//	@org.junit.Test
	public void testTransaction()
	{
		RestResult r = new RestResult();
		
		System.out.println(r.getMsg());
		
		TbDemo demo = new TbDemo();
		demo.setTcCode("c1");
		demo.setTcName("n1");
		try
		{
			service.add(demo);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void testRedissonLock()
	{
		// 并发数
		Integer size = 2;
		// 定义线程池
		ExecutorService executorService = Executors.newFixedThreadPool(size);

		for (int i = 0; i < size; i++)
		{
			executorService.execute(() ->
			{
				TbDemo demo = new TbDemo();
				demo.setTcCode("c1_lock");
				demo.setTcName("n1_lock");
				try
				{
					service.addWithRedissonLock(demo);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			});
		}
	}
}
