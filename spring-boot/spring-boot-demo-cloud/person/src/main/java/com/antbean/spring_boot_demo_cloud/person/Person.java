package com.antbean.spring_boot_demo_cloud.person;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {
	@Id
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}

	public Person(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Person(String name) {
		super();
		this.name = name;
	}

	public Person() {
		super();
	}

}
