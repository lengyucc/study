package com.antbean.springbootdemodatatx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	@Autowired
	PersonService personService;

	@RequestMapping("rollback")
	public Person rollback(Person person) {
		return personService.savePersonWithRollBack(person);
	}

	@RequestMapping("noRollback")
	public Person noRollback(Person person) {
		return personService.savePersonWithoutRollBack(person);
	}
}
