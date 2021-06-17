package com.safety.savinglives.safetynetapplication.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.repository.FireStationRepository;

@Service
public class FireStationServices {

	@Autowired
	FireStationRepository fireStationRepository;

	public FireStationServices() {
		super();
	}

	public Iterable<FireStation> getAllFireStations() {

		return fireStationRepository.getAllData();
	}

	public Boolean saveANewStation(FireStation caserne) {
		// TODO Auto-generated method stub
		return fireStationRepository.save(caserne);
	}

	public Boolean deleteANewStation(String address) {

		for (FireStation fs : fireStationRepository.getAllData()) {

			if (fs.getAddress().equals(address)) {

				return fireStationRepository.delete(fs);
			}

		}

		return false;
	}

	public FireStation updateAStation(String address, String newId) {

		List<FireStation> copyFireStationList = fireStationRepository.getAllData();
		FireStation newStation = null;

		for (FireStation fs : copyFireStationList) {

			for (int i = 0; i < copyFireStationList.size(); i++) {

				if (copyFireStationList.get(i).getAddress().equals(address)) {

					newStation = copyFireStationList.get(i);
					newStation.setStation(newId);
					fireStationRepository.update(i, newStation);
				}
			}
		}

		return newStation;
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
