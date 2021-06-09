package com.safety.testone.testoneofsafetynet.repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.safety.testone.testoneofsafetynet.CustomProperties;
import com.safety.testone.testoneofsafetynet.model.Person;

@Component
public class PersonRepository {

	@Autowired
	CustomProperties customProperties;

	@Autowired
	private DAOFactory generalDAO;

	private GeneralDataRepository gen;
	private List<Person> personList;

	public PersonRepository() {

	}

	public List<Person> getAllPersons() {

		if (gen == null) {
			Instantiate();
		}

		return personList;

	}

	private void Instantiate() {

		gen = generalDAO.loadDataFromFile();
		personList = gen.getPersons();

	}

	public Person saveAPerson(Person person) {

		if (gen == null) {
			Instantiate();
		}

		personList.add(person);

		return person;
	}

	public Person deleteAPerson(String firstName, String lastName) {

		if (gen == null) {
			Instantiate();
		}

		for (Person p : personList) {

			if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {

				Person winner = p;
				personList.remove(p);
				return p;
			}
		}

		return null;
	}

	public Person updateAPerson(Person P) {

		if (gen == null) {
			Instantiate();
		}

		for (int i = 0; i < personList.size(); i++) {

			if (personList.get(i).getFirstName().equals(P.getFirstName())
					&& personList.get(i).getLastName().equals(P.getLastName())) {

				personList.set(i, P);
				return P;
			}

		}

		return null;
	}

}
