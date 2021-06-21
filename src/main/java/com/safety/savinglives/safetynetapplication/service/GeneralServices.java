package com.safety.savinglives.safetynetapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.savinglives.safetynetapplication.repository.DAOFactory;
import com.safety.savinglives.safetynetapplication.repository.GeneralDataRepository;

@Service
public class GeneralServices {

	@Autowired
	DAOFactory gen;

	public GeneralServices() {
		super();
	}

	public GeneralDataRepository loadDataFromFile() {
		// TODO Auto-generated method stub
		return gen.loadDataFromFile();
	}

}
