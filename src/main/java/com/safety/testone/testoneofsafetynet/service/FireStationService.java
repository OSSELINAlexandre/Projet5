package com.safety.testone.testoneofsafetynet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.Person;
import com.safety.testone.testoneofsafetynet.repository.FireStationRepository;

@Service
public class FireStationService {

	@Autowired
	FireStationRepository fireStationRepository;

	public FireStationService() {
		super();
	}

	public Iterable<FireStation> getAllFireStations() {

		return fireStationRepository.getAllFireStations();
	}

	public FireStation saveANewStation(FireStation caserne) {
		// TODO Auto-generated method stub
		return fireStationRepository.saveTheStation(caserne);
	}

	public Boolean deleteANewStation(String address) {

		for (FireStation fs : fireStationRepository.getAllFireStations()) {

			if (fs.getAddress().equals(address)) {

				return fireStationRepository.deleteTheStation(fs);
			}

		}

		return false;
	}

	public FireStation updateAStation(String address, String newId) {

		List<FireStation> copyFireStationList = fireStationRepository.getAllFireStations();
		FireStation newStation = new FireStation();

		for (FireStation fs : copyFireStationList) {

			for (int i = 0; i < copyFireStationList.size(); i++) {

				if (copyFireStationList.get(i).getAddress().equals(address)) {

					newStation = copyFireStationList.get(i);
					newStation.setStation(newId);
					fireStationRepository.updateTheStation(i, newStation);
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
