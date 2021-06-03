package com.safety.testone.testoneofsafetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safety.testone.testoneofsafetynet.model.Person;
import com.safety.testone.testoneofsafetynet.model.PersonDAO;

@RestController
public class SafetyController {

	@Autowired
	PersonDAO personDAO;

	@GetMapping(value = "/person")
	public Object printAllPeople() {

		return personDAO.getAllValues();
	}

}
