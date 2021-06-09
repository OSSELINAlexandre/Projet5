package com.safety.testone.testoneofsafetynet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.repository.GeneralDataRepository;
import com.safety.testone.testoneofsafetynet.repository.DAOFactory;

@Service
public class GeneralService {

	@Autowired
	DAOFactory gen;
	
	
	public GeneralService() {
		super();
	}

	public GeneralDataRepository loadDataFromFile() {
		// TODO Auto-generated method stub
		return gen.loadDataFromFile();
	}

}
