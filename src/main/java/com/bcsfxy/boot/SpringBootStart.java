package com.bcsfxy.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
/*
 * @EnableAutoConfiguration
 * 
 * @ComponentScan(basePackages = { "com.bcsfxy.config" ,"com.bcsfxy.filter"
 * ,"com.bcsfxy.dao" ,"com.bcsfxy.controller" ,"com.bcsfxy.service" })
 */
@EnableScheduling // 启用间隔调度
public class SpringBootStart extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootStart.class);
	}

	public static void main(String[] args) throws Exception{
		SpringApplication.run(SpringBootStart.class, args);
	}
}
