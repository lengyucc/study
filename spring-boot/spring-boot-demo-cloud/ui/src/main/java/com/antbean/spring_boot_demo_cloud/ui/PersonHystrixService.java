package com.antbean.spring_boot_demo_cloud.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class PersonHystrixService {
	@Autowired
	private PersonService personService;

	@HystrixCommand(fallbackMethod = "fallbackSave") // 1
														// 使用@HystrixCommand的fallbackMethod参数指定,当本方式调用失败后,调用后备方法fallbackSave
	public Object save(String name) {
		return personService.save(name);
	}

	public Object fallbackSave() {
		return "Person Service 故障";
	}
}
