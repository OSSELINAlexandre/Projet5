package com.safety.testone.testoneofsafetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.FireStationDAO;

@Service
public class FireStationService {

	@Autowired
	FireStationDAO fireStationDAO;

	public FireStationService() {
		super();
	}

	public Iterable<FireStation> getAllFireStations() {

		return fireStationDAO.getAllFireStations();
	}

}
