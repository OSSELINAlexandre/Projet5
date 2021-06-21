package com.safety.savinglives.safetynetapplication.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.util.CustomProperties;

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
