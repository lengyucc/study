package com.antbean.springbootdemodatatx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoServiceImpl implements DemoService {

	@Autowired
	PersonRepository personRepository;
	@Autowired
	DemoService demoService;

	@Transactional
	@Override
	public void save1() {
		Person person21 = new Person(21L, "test", 10, "test");
		personRepository.save(person21);

		try {
//			save2();	// 这样调用的话，save2的事务是不起作用的，原因在于spring返回的bean是一个代理对象，代理对象里调用真正的对象方法
			//http://blog.csdn.net/aya19880214/article/details/50640596
			demoService.save2();
		} catch (Exception e) {
			System.out.println("捕获到save2抛出的异常");
			e.printStackTrace();
		}

		Person person22 = new Person(22L, "test", 10, "test");
		personRepository.save(person22);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { RuntimeException.class })
	@Override
	public void save2() {
		Person person31 = new Person(31L, "test", 10, "test");
		personRepository.save(person31);

		if ("".equals("")) {
			throw new RuntimeException("save2抛出异常");
		}

		Person person32 = new Person(32L, "test", 10, "test");
		personRepository.save(person32);
	}

}
