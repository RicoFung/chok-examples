package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

//默认（只能扫描project内的bean）
//@SpringBootApplication
//自定义（可扫描project外的bean）
@ComponentScan(basePackages={"com","chok.common"})
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer
{
	public static void main(String[] args)
	{
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(Application.class);
	}
}

//@SpringBootApplication
//public class Application
//{
//	public static void main(String[] args)
//	{
//		SpringApplication.run(Application.class, args);
//	}
//}
