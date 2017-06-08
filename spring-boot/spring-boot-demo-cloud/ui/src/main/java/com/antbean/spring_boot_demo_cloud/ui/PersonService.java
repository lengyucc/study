package com.antbean.spring_boot_demo_cloud.ui;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/*
 * 
 * 只需通过简单的在接口中声明方法即可调用Person服务的Rest服务
 * 
 */
@FeignClient("person")
public interface PersonService {
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	Object save(@RequestBody String name);
}
