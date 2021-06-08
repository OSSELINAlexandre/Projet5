package com.safety.testone.testoneofsafetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	public Iterable<Person> getAllPerson() {

		return personService.getAllPersons();
	}

	@PostMapping(value = "/person")
	public Person saveAPerson(@RequestBody Person person) {
		return personService.saveANewPerson(person);
	}

	@DeleteMapping(value = "/person/{firstName}/{thelastName}")
	public Person createAPerson(@PathVariable("firstName") String firstName, @PathVariable("thelastName") String thelastName) {

		return personService.deleteAPerson(firstName, thelastName);

	}

	@PutMapping(value = "/person")
	public Person updateAPerson(@RequestBody Person p) {

		return personService.updateAPerson(p);
	}
}
