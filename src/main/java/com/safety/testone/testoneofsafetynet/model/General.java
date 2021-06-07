package com.safety.testone.testoneofsafetynet.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class General {

	private List<Person> persons;
	private List<FireStation> firestations;
	private List<Medicalrecords> medicalrecords;

	public General(List<Person> persons, List<FireStation> firestations, List<Medicalrecords> medicalrecords) {
		super();
		this.persons = persons;
		this.firestations = firestations;
		this.medicalrecords = medicalrecords;
	}

	public General() {
		super();
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<FireStation> getFirestations() {
		return firestations;
	}

	public void setFirestations(List<FireStation> firestations) {
		this.firestations = firestations;
	}

	public List<Medicalrecords> getMedicalrecords() {
		return medicalrecords;
	}

	public void setMedicalrecords(List<Medicalrecords> medicalrecords) {
		this.medicalrecords = medicalrecords;
	}

}
