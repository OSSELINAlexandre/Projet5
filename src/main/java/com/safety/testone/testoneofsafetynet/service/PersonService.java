package com.safety.testone.testoneofsafetynet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.model.Person;
import com.safety.testone.testoneofsafetynet.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository personRepository;

	public PersonService() {
		super();
	}

	public Iterable<Person> getAllPersons() {

		return personRepository.getAllPersons();
	}

	public Person saveANewPerson(Person person) {

		return personRepository.saveAPerson(person);
	}

	public Person deleteAPerson(String firstName, String lastName) {

		return personRepository.deleteAPerson(firstName, lastName);
	}

	public Person updateAPerson(Person p) {

		return personRepository.updateAPerson(p);
	}

}
