package uk.gov.dwp.uc.pairtest.entities;

import java.util.*;
import uk.gov.dwp.uc.pairtest.helpers.Helpers;

/* Person class for registering a customer */
public class Person {

	Helpers helpers = new Helpers();
	List<Person> persons = new ArrayList<>();
	
	String name;
	int age;
	
	Person registerPerson(String name, int age)
	{	
		if(helpers.validatePerson(name, age))
		{
			Person person = new Person().setAge(age).setName(name);
			persons.add(person);
		}
		
		return this;
	}
	
	public List<Person> getPersons()
	{
		return this.persons;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getAge()
	{
		return this.age;
	}
	
	Person setName(String name)
	{
		this.name = name;
		return this;
	}
	
	Person setAge(int age)
	{
		this.age = age;
		return this;
	}
	
	
	
}
