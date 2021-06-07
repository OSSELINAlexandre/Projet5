package com.safety.testone.testoneofsafetynet.model;

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

@Component
public class PersonDAO {

	@Autowired
	CustomProperties customProperties;

	@Autowired
	General gen;

	@Autowired
	generalDAO genDAO;

	public PersonDAO() {

	}

	public List<Person> getAllPersons() {

		gen = genDAO.loadDataFromFile();

		List<Person> result = gen.getPersons();

		return result;

	}

}
