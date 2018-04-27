package com.orangeslate.naturestore.repository;

import java.util.List;

import com.mongodb.WriteResult;
import com.orangeslate.naturestore.domain.Person;

public interface Repository {

	public List<Person> getAllPersons();

	public void savePerson(Person tree);

	public WriteResult updatePerson(String id, String name);

	public void deletePerson(String id);

	public void createCollection();

	public void dropCollection();
	
	public String loginCheck(String id,String password);

}
