package com.atnbean.springbootdemojpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
	@Autowired
	PersonRepository personRepository;
  
	@RequestMapping("/save")
	public Person save(String name, String address, Integer age) {
		Person p = personRepository.save(new Person(System.currentTimeMillis(),name, age, address));
		return p;
	}
 
	@RequestMapping("/q1")
	public List<Person> q1(String address) {
		return personRepository.findByAddress(address);
	}

	@RequestMapping("/q2")
	public Person q2(String name, String address) {
		return personRepository.findByNameAndAddress(name, address);
	}

	@RequestMapping("/q3")
	public Person q3(String name, String address) {
		return personRepository.withNameAndAddressQuery(name, address);
	}

	@RequestMapping("/q4")
	public List<Person> q4(String name, String address) {
		return personRepository.withNameAndAddressNamedQuery(name, address);
	}

	@RequestMapping("/sort")
	public List<Person> sort() {
		return personRepository.findAll(new Sort(Direction.ASC, "age"));
	}

	@RequestMapping("/page")
	public Page<Person> page() {
		return personRepository.findAll(new PageRequest(1, 5));
	}
}
