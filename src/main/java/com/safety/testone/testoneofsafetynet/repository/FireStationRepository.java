package com.safety.testone.testoneofsafetynet.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.MedicalRecord;

@Component
public class FireStationRepository {

	@Autowired
	private DAOFactory generalDAO;

	private GeneralDataRepository gen;

	private List<FireStation> fireStationList;

	public FireStationRepository() {
		super();
	}

	public List<FireStation> getFireStationList() {
		return fireStationList;
	}

	public void setFireStationList(List<FireStation> fireStationList) {
		this.fireStationList = fireStationList;
	}

	public List<FireStation> getAllFireStations() {

		if (gen == null)
			Instantiate();

		return fireStationList;

	}

	private void Instantiate() {
		gen = generalDAO.loadDataFromFile();
		fireStationList = gen.getFirestations();
	}

	public FireStation saveTheStation(FireStation caserne) {

		if (gen == null)
			Instantiate();

		fireStationList.add(caserne);
		return caserne;
	}

	public Boolean deleteTheStation(FireStation wantedtoDelete) {

		if (gen == null)
			Instantiate();
		
		return fireStationList.remove(wantedtoDelete);
	}

	public FireStation updateTheStation(int i, FireStation newItem) {

		if (gen == null)
			Instantiate();

		
		return fireStationList.set(i, newItem);

		
	}

}
