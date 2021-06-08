package com.safety.testone.testoneofsafetynet.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.MedicalRecord;
import com.safety.testone.testoneofsafetynet.model.Person;

@Component
public class GeneralData {

	private List<Person> persons;
	private List<FireStation> firestations;
	private List<MedicalRecord> medicalRecord;

	public GeneralData(List<Person> persons, List<FireStation> firestations, List<MedicalRecord> medicalRecord) {
		super();
		this.persons = persons;
		this.firestations = firestations;
		this.medicalRecord = medicalRecord;
	}

	public GeneralData() {
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

	public List<MedicalRecord> getMedicalrecords() {
		return medicalRecord;
	}

	public void setMedicalrecords(List<MedicalRecord> medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

}
