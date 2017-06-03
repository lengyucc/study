package com.antbean.springbootdemodataredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	@Autowired
	PersonDao personDao;

	@RequestMapping("/set")
	public void set() {
		Person person = new Person("1", "lmh", 22);
		personDao.save(person);
		personDao.stringRedisTemplateDemo();
	}

	@RequestMapping("/getStr")
	public String getStr() {
		return personDao.getString();
	}

	@RequestMapping("/getPerson")
	public Person getPerson() {
		return personDao.getPerson();
	}

}
