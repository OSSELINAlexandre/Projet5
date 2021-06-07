package com.safety.testone.testoneofsafetynet.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FireStationDAO {

	@Autowired
	General gen;

	@Autowired
	generalDAO genDAO;

	public FireStationDAO() {
		super();
	}

	

	public List<FireStation> getAllFireStations() {

		gen = genDAO.loadDataFromFile();

		List<FireStation> result = gen.getFirestations();

		return result;

	}
	
}
