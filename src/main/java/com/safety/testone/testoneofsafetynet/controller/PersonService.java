package com.safety.testone.testoneofsafetynet.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.model.Person;
import com.safety.testone.testoneofsafetynet.model.PersonDAO;

@Service
public class PersonService {

	@Autowired
	PersonDAO personDAO;

	private List<Person> persons;

	public PersonService() {
		super();
	}

	public Iterable<Person> getAllPersons() {
		if (persons == null) {
			Instantiate();
		}

		return persons;
	}

	public Person saveANewPerson(Person person) {

		if (persons == null) {
			Instantiate();
		}

		persons.add(person);

		return person;

	}

	private void Instantiate() {

		persons = personDAO.getAllPersons();
	}

	public Person deleteAPerson(String firstName, String lastName) {

		if (persons == null) {
			Instantiate();
		}

		for (Person p : persons) {

			if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {

				Person deletedResult = p;
				persons.remove(p);
				return deletedResult;
			}
		}

		Person person = new Person();
		person.setFirstName("Incongnito");
		person.setLastName("RealIncognito");
		return person;
	}

	public List<Person> getThefamillyStuff(String name) {

		ArrayList<Person> theFamilly = new ArrayList<Person>();

		for (Person p : persons) {

			if (p.getLastName().equals(name))
				theFamilly.add(p);
		}

		return theFamilly;
	}

}
