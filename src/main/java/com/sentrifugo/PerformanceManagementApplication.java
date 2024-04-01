package com.sentrifugo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class  PerformanceManagementApplication {
	public static void main(String[] args) {

		SpringApplication.run(PerformanceManagementApplication.class,args);
		System.out.println("Application started");
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(PerformanceManagementApplication.class);
//	} extends SpringBootServletInitializer

}

