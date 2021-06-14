package com.safety.savinglives.safetynetapplication.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.service.PersonService;

@RestController
public class PersonController {

	private static final Logger logger = LogManager.getLogger(PersonController.class);

	@Autowired
	PersonService personService;

	@GetMapping(value = "/person")
	public ResponseEntity<Iterable<Person>> getAllPerson() {

		Iterable<Person> result = personService.getAllPersons();

		if (result == null) {
			logger.info("Successfully return a satisfying result for GET /person ");
			return ResponseEntity.notFound().build();
		} else {
			logger.error("COULD NOT return a satisfying result for GET /person ");
			return ResponseEntity.ok().body(result);
		}
	}

	@PostMapping(value = "/person")
	public ResponseEntity<Void> saveAPerson(@RequestBody Person person) {

		Boolean result = personService.saveANewPerson(person);

		if (result) {

			logger.info("Successfully return a satisfying result for POST /person ");
			return ResponseEntity.ok().build();
		} else {

			logger.error("COULD NOT return a satisfying result for POST /person ");
			return ResponseEntity.notFound().build();
		}

	}

	@DeleteMapping(value = "/person/{firstName}/{thelastName}")
	public ResponseEntity<Void> deleteAPerson(@PathVariable("firstName") String firstName,
			@PathVariable("thelastName") String thelastName) {

		Boolean result = personService.deleteAPerson(firstName, thelastName);

		if (result) {
			logger.info("Successfully return a satisfying result for DELETE /person ");
			return ResponseEntity.ok().build();
		} else {
			logger.error("COULD NOT return a satisfying result for DELETE /person ");
			return ResponseEntity.notFound().build();
		}

	}

	@PutMapping(value = "/person")
	public ResponseEntity<Void> updateAPerson(@RequestBody Person p) {

		Person result = personService.updateAPerson(p);

		
		if (result != null) {
			logger.info("Successfully return a satisfying result for PUT /person ");
			return ResponseEntity.ok().build();
		} else {
			logger.error("COULD NOT return a satisfying result for PUT /person ");
			return ResponseEntity.notFound().build();
		}

	}
}
