package com.antbean.spring_boot_demo_cloud.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class SomeHystrixService {
	@Autowired
	private RestTemplate restTemplate; // 1 在Spring Boot下使用Ribbon,只需注入一个RestTemplate即可,Spring Boot已经做好了配置

	@HystrixCommand(fallbackMethod = "fallbackSome") // 2
	public String getSome() {
		return restTemplate.getForObject("http://some/getsome", String.class);
	}

	public String fallbackSome() {
		return "Some Service 故障";
	}
}
