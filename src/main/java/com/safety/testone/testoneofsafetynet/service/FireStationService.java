package com.safety.testone.testoneofsafetynet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.DAO.FireStationDAO;
import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.Person;

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

	public FireStation saveANewStation(FireStation caserne) {
		// TODO Auto-generated method stub
		return fireStationDAO.saveTheStation(caserne);
	}

	public FireStation deleteANewStation(String address) {
		// TODO Auto-generated method stub
		return fireStationDAO.deleteTheStation(address);
	}

	public FireStation updateAStation(String address, String newId) {
		// TODO Auto-generated method stub
		return fireStationDAO.updateTheStation(address, newId);
	}

	public List<FireStation> selectSomeFireStation(String stationNumber) {

		Iterable<FireStation> wholeThing = getAllFireStations();
		List<FireStation> resultThing = new ArrayList<FireStation>();
		for (FireStation fs : wholeThing) {

			if (fs.getStation().equals(stationNumber)) {
				resultThing.add(fs);
			}

		}

		return resultThing;
	}

}
