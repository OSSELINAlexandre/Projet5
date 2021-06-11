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

		return personRepository.getAllData();
	}

	public Boolean saveANewPerson(Person person) {

		return personRepository.save(person);
	}

	public Boolean deleteAPerson(String firstName, String lastName) {

		for (Person p : personRepository.getAllData()) {

			if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {

				return personRepository.delete(p);

			}
		}

		return false;
	}

	public Person updateAPerson(Person p) {

		Person result = null;
		List<Person> copyOfOriginial = personRepository.getAllData();
		for (int i = 0; i < copyOfOriginial.size(); i++) {

			if (copyOfOriginial.get(i).getFirstName().equals(p.getFirstName())
					&& copyOfOriginial.get(i).getLastName().equals(p.getLastName())) {
				result = personRepository.update(i, p);

			}
		}

		return result;
	}

}
