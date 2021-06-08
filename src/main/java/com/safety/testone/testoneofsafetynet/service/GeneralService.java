package com.safety.testone.testoneofsafetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.repository.GeneralData;
import com.safety.testone.testoneofsafetynet.repository.generalDataDAO;

@Service
public class GeneralService {

	@Autowired
	generalDataDAO gen;
	
	
	public GeneralService() {
		super();
	}

	public GeneralData loadDataFromFile() {
		// TODO Auto-generated method stub
		return gen.loadDataFromFile();
	}

}
