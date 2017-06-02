package com.antbean.springbootdemodatacache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootDemoDataCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoDataCacheApplication.class, args);
	}
}
