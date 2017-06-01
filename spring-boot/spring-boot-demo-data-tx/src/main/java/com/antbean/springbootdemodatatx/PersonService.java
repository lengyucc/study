package com.antbean.springbootdemodatatx;

public interface PersonService {
	Person savePersonWithRollBack(Person person);

	Person savePersonWithoutRollBack(Person person);

}
