package com.safety.testone.testoneofsafetynet.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.MedicalRecord;
import com.safety.testone.testoneofsafetynet.repository.GeneralData;
import com.safety.testone.testoneofsafetynet.repository.generalDataDAO;

@Component
public class FireStationDAO {

	@Autowired
	private generalDataDAO generalDAO;

	private GeneralData gen;

	private List<FireStation> fireStationList;

	public FireStationDAO() {
		super();
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

	public FireStation deleteTheStation(String address) {

		if (gen == null)
			Instantiate();

		for (FireStation fs : fireStationList) {

			if (fs.getAddress().equals(address)) {

				FireStation fire = fs;
				fireStationList.remove(fs);
				return fire;
			}

		}

		return null;
	}

	public FireStation updateTheStation(String adress, String numero) {

		if (gen == null)
			Instantiate();

		for (FireStation fs : fireStationList) {

			for (int i = 0; i < fireStationList.size(); i++) {

				if (fireStationList.get(i).getAddress().equals(adress)) {

					FireStation newStation = fireStationList.get(i);
					newStation.setStation(numero);
					;
					fireStationList.set(i, newStation);
					return newStation;
				}

			}

		}

		return null;
	}

}
