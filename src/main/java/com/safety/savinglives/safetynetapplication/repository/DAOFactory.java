package com.safety.savinglives.safetynetapplication.repository;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.savinglives.safetynetapplication.util.CustomProperties;

@Component
public class DAOFactory {

	@Autowired
	CustomProperties customProperties;
	


	public DAOFactory() {
	}

	public GeneralDataRepository loadDataFromFile() {

		GeneralDataRepository gen = new GeneralDataRepository();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			gen = objectMapper.readValue(new File(customProperties.getFileLoc()), GeneralDataRepository.class);
		} catch (IOException e) {
			System.out.println("Error in the loading of the file");
			e.printStackTrace();
		}
		
		return gen;

	}
	


}
