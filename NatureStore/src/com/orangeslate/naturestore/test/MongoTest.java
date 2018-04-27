package com.orangeslate.naturestore.test;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.orangeslate.naturestore.domain.Person;
import com.orangeslate.naturestore.repository.NatureRepositoryImpl;
import com.orangeslate.naturestore.repository.Repository;

public class MongoTest {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext.xml");

		// Repository repository =
		// (Repository)context.getBean("natureRepository");

		Repository repository = context.getBean(NatureRepositoryImpl.class);

		// cleanup collection before insertion
		repository.dropCollection();

		// create collection
		repository.createCollection();
		
		//Crud operation
		
		//Crud operation for insert
		repository.savePerson(new Person("1", "AAA", 10,"1234"));
		repository.savePerson(new Person("2", "BBB", 3,"12345"));

		//Crud operation for Update
		repository.updatePerson("1", "DDD");
		
		//Crud operation for Delete
		repository.deletePerson("2");
		
		//Crud operation for get/read
		System.out.println("Read. " + repository.getAllPersons());
		
		//Brute Force
		System.out.println(repository.loginCheck("AAA", "rrrr"));
		System.out.println(repository.loginCheck("AAA", "jjjjjj"));
		System.out.println(repository.loginCheck("AAA", "lllll"));
		

	}
}
