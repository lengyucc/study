package com.antbean.springbootdemodatatx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonRepository personRepository;

	@Transactional(rollbackFor = { IllegalArgumentException.class })
	@Override
	public Person savePersonWithRollBack(Person person) {
		Person p = personRepository.save(person);
		if (person.getName().equals("汪云飞")) {
			throw new IllegalArgumentException("汪云飞已存在,数据将回滚");
		}
		return p;
	}

	@Transactional/*(noRollbackFor = { IllegalArgumentException.class })*/
	@Override
	public Person savePersonWithoutRollBack(Person person) {
		Person p = personRepository.save(person);
		if (person.getName().equals("汪云飞")) {
			throw new IllegalArgumentException("汪云飞虽已存在,数据将不会回滚");
		}
		return p;
	}

}
