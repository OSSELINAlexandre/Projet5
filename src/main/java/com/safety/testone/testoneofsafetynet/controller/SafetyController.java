package com.safety.testone.testoneofsafetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	PersonService personService;

	@Autowired
	FireStationService fireStationService;

	@Autowired
	MedicalRecordService medicalRecordService;

	@Autowired
	GeneralService gen;

	@GetMapping(value = "/person")
	public Iterable<Person> printAllPeople() {

		return personService.getAllPersons();
	}
	
	

	@PostMapping(value = "/person")
	public Person saveANewPersonFromPost(@RequestBody Person person) {

		return personService.saveANewPerson(person);
	}

	@DeleteMapping(value = "/person/{firstName}/{thelastName}")
	public Person updateAPerson(@PathVariable("firstName") String firstName, @PathVariable("thelastName") String thelastName) {

		return personService.deleteAPerson(firstName, thelastName);

	}

	@GetMapping(value = "/person/{name}")
	public List<Person> getAFamilly(@PathVariable String name) {

		return personService.getThefamillyStuff(name);
	}

	@GetMapping(value = "/firestation")
	public Iterable<FireStation> printAllFireStations() {

		return fireStationService.getAllFireStations();
	}

	@GetMapping(value = "/medicalrecords")
	public Iterable<Medicalrecords> printAllMedicalrecords() {

		return medicalRecordService.getAllMedicalRecords();
	}

	@GetMapping(value = "/Test")
	public General printAllFile() {
		return gen.loadDataFromFile();
	}

}
