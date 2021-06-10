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
import com.safety.testone.testoneofsafetynet.model.MedicalRecord;
import com.safety.testone.testoneofsafetynet.model.Person;

@Component
public class PersonRepository implements DAOMethods<Person> {

	@Autowired
	CustomProperties customProperties;

	@Autowired
	private DAOFactory generalDAO;

	private GeneralDataRepository gen;
	private List<Person> personList;

	public PersonRepository() {

	}

	private void Instantiate() {

		gen = generalDAO.loadDataFromFile();
		personList = gen.getPersons();

	}

	@Override
	public Boolean save(Person t) {
		if (gen == null) {
			Instantiate();
		}

		return personList.add(t);
	}

	@Override
	public Boolean delete(Person t) {

		if (personList.remove(t)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Person update(int i, Person t) {
		if (gen == null) {
			Instantiate();
		}

		return personList.set(i, t);

	}

	@Override
	public List<Person> getAllData() {
		
		if (gen == null) {
			Instantiate();
		}

		return personList;
	}

}
