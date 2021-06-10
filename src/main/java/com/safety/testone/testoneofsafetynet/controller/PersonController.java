package com.safety.testone.testoneofsafetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safety.testone.testoneofsafetynet.model.Person;
import com.safety.testone.testoneofsafetynet.service.PersonService;

@RestController
public class PersonController {

	@Autowired
	PersonService personService;

	@GetMapping(value = "/person")
	public ResponseEntity<Iterable<Person>> getAllPerson() {

		Iterable<Person> result = personService.getAllPersons();

		if (result == null) {

			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok().body(result);
		}
	}

	@PostMapping(value = "/person")
	public ResponseEntity<Void> saveAPerson(@RequestBody Person person) {

		Boolean result = personService.saveANewPerson(person);

		if (result) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@DeleteMapping(value = "/person/{firstName}/{thelastName}")
	public ResponseEntity<Void> deleteAPerson(@PathVariable("firstName") String firstName,
			@PathVariable("thelastName") String thelastName) {

		Boolean result = personService.deleteAPerson(firstName, thelastName);

		if (result) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PutMapping(value = "/person")
	public ResponseEntity<Void> updateAPerson(@RequestBody Person p) {

		Person result = personService.updateAPerson(p);

		if (result == null) {

			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.accepted().build();
		}

	}
}
