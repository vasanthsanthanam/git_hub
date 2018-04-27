package com.orangeslate.naturestore.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;
import com.orangeslate.naturestore.domain.Person;

public class NatureRepositoryImpl implements Repository {

	MongoTemplate mongoTemplate;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * getAllPersons.
	 */
	public List<Person> getAllPersons() {
		return mongoTemplate.findAll(Person.class);
	}

	/**
	 * savePerson.
	 */
	public void savePerson(Person person) {
		mongoTemplate.insert(person);
	}

	/**
	 * updatePerson.
	 */
	public WriteResult updatePerson(String id, String name) {
		return mongoTemplate.updateFirst(
				new Query(Criteria.where("id").is(id)),
				Update.update("name", name), Person.class);
	}

	/**
	 * deletePerson.
	 */
	public void deletePerson(String id) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Person.class);
	}
	
	/**
	 * loginCheck.
	 */
	@Override
	public String loginCheck(String name, String password) {
		
		Person person = (Person) mongoTemplate.find(new Query(Criteria.where("name").is(name)), Person.class);
		int activeCount = ((person.getActiveCount() != 0 )?person.getActiveCount():0);
		if(person != null && person.isActive()) {
			person = (Person) mongoTemplate.find(new Query(Criteria.where("password").is(password)), Person.class);
			if(person == null) {
				mongoTemplate.updateFirst(new Query(Criteria.where("name").is(name)),
						Update.update("activeCount", activeCount+1), Person.class);
				if(activeCount >2) {
					mongoTemplate.updateFirst(new Query(Criteria.where("name").is(name)),
							Update.update("active", false), Person.class);
				}{
					return "login failed";
				}
			}else {
				return "login success";
			}
		}
		return "login account locked.";
	}

	/**
	 * Create a {@link Person} collection if the collection does not already
	 * exists
	 */
	public void createCollection() {
		if (!mongoTemplate.collectionExists(Person.class)) {
			mongoTemplate.createCollection(Person.class);
		}
	}

	/**
	 * Drops the {@link Person} collection if the collection does already exists
	 */
	public void dropCollection() {
		if (mongoTemplate.collectionExists(Person.class)) {
			mongoTemplate.dropCollection(Person.class);
		}
	}

}
