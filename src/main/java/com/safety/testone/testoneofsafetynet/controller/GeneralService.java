package com.safety.testone.testoneofsafetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.model.General;
import com.safety.testone.testoneofsafetynet.model.generalDAO;

@Service
public class GeneralService {

	@Autowired
	generalDAO gen;
	
	
	public GeneralService() {
		super();
	}

	public General loadDataFromFile() {
		// TODO Auto-generated method stub
		return gen.loadDataFromFile();
	}

}
