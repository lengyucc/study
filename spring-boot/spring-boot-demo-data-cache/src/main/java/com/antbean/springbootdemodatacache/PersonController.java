package com.antbean.springbootdemodatacache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	@Autowired
	PersonService personService;

	@RequestMapping("/put")
	public Person put(Person person) {
		return personService.save(person);
	}

	@RequestMapping("/remove")
	public String remove(Person person) {
		personService.remove(person.getId());
		return "ok";
	}

	@RequestMapping("/findOne")
	public Person findOne(Person person) {
		return personService.findOne(person);
	}
}
