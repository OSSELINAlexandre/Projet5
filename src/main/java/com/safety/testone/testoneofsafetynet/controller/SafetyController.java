package com.safety.testone.testoneofsafetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.FireStationDAO;
import com.safety.testone.testoneofsafetynet.model.General;
import com.safety.testone.testoneofsafetynet.model.MedicalRecordDAO;
import com.safety.testone.testoneofsafetynet.model.Medicalrecords;
import com.safety.testone.testoneofsafetynet.model.Person;
import com.safety.testone.testoneofsafetynet.model.PersonDAO;
import com.safety.testone.testoneofsafetynet.model.generalDAO;

@RestController
public class SafetyController {

	@Autowired
	PersonDAO personDAO;

	@Autowired
	FireStationDAO fireStationDAO;

	@Autowired
	MedicalRecordDAO medicalRecordDAO;

	@Autowired
	generalDAO gen;

	@GetMapping(value = "/person")
	public Iterable<Person> printAllPeople() {

		return personDAO.getAllPersons();
	}

	@GetMapping(value = "/firestation")
	public Iterable<FireStation> printAllFireStations() {

		return fireStationDAO.getAllFireStations();
	}

	@GetMapping(value = "/medicalrecords")
	public Iterable<Medicalrecords> printAllMedicalrecords() {

		return medicalRecordDAO.getAllMedicalRecords();
	}

	@GetMapping(value = "/Test")
	public General printAllFile() {
		return gen.loadDataFromFile();
	}

}
