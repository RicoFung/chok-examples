package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

// 默认（只能扫描project内的bean）
//@SpringBootApplication
// 自定义（可扫描project外的bean）
@ComponentScan(basePackages={"com","chok.common"})
// exclude表示自动配置时排除特定配置（使用CommonsMultipartFile实现多文件上传）
@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
@EnableScheduling
@EnableAsync
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
