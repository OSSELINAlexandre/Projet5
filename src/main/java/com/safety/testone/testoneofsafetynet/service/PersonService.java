package com.safety.testone.testoneofsafetynet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.DAO.PersonDAO;
import com.safety.testone.testoneofsafetynet.model.Person;

@Service
public class PersonService {

	@Autowired
	PersonDAO personDAO;

	public PersonService() {
		super();
	}

	public Iterable<Person> getAllPersons() {

		return personDAO.getAllPersons();
	}

	public Person saveANewPerson(Person person) {

		return personDAO.saveAPerson(person);
	}

	public Person deleteAPerson(String firstName, String lastName) {

		return personDAO.deleteAPerson(firstName, lastName);
	}

	public Person updateAPerson(Person p) {

		return personDAO.updateAPerson(p);
	}

}
