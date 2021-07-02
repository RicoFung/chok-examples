import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Application;
import com.fasterxml.jackson.databind.ObjectMapper;

import chok.component.SnBuilder;
import chok.util.TimeUtil;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Test
{
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	SnBuilder snBuilder;
	
//	private ObjectMapper restMapper = new ObjectMapper();
	
	@org.junit.Test
	public void testGenSn()
	{
		// 并发数, 即：Jmeter 中线程数
		Integer threadSize = 2;
		// 定义线程池
		ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
		// 线程并发执行
		for (int i = 0; i < threadSize; i++)
		{
			final int index = i;
			executorService.execute(() ->
			{
				String[] snArray = snBuilder.buildBatch(TimeUtil.getCurrentTime("yyyyMMdd"), 3, 5);
				log.info("【线程{}】 ==> {}", index+1, Arrays.toString(snArray));
				System.out.println("【线程"+(index+1)+"】 ==> " + Arrays.toString(snArray));
			});
		}
		// 等待1s
		try
		{
			Thread.sleep(20000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		// 关闭线程池
		executorService.shutdown();
	}
}
