package com.antbean.springbootdemodatamongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	@Autowired
	PersonRepository personRepository;

	@RequestMapping("/q1")
	public Person q1(String name) {
		return personRepository.findByName(name);
	}

	@RequestMapping("/q2")
	public List<Person> q2(Integer age) {
		return personRepository.withQueryFindByAge(age);
	}

	@RequestMapping("/save")
	public Person save() {
		Person p = new Person("lmh", 22);
		p.getLocations().add(new Location("上海", "2009"));
		p.getLocations().add(new Location("合肥", "2010"));
		p.getLocations().add(new Location("广州", "2011"));
		p.getLocations().add(new Location("马鞍山", "2012"));
		return personRepository.save(p);
	}
}
