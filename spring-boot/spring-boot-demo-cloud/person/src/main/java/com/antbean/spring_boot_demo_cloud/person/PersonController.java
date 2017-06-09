package com.antbean.spring_boot_demo_cloud.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	@Autowired
	private PersonRepository personRepository;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object savePerson(@RequestBody String personName) {
		System.out.println("PersonController.savePerson() ################");
		Person p = new Person(personName);
		personRepository.save(p);
		return personRepository.findAll(new PageRequest(0, 10)).getContent();
	}
}
