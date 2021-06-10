package com.safety.testone.testoneofsafetynet.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.MedicalRecord;

@Component
public class FireStationRepository implements DAOMethods<FireStation> {

	@Autowired
	private DAOFactory generalDAO;

	private GeneralDataRepository gen;

	private List<FireStation> fireStationList;

	public FireStationRepository() {
		super();
	}

	public DAOFactory getGeneralDAO() {
		return generalDAO;
	}

	public void setGeneralDAO(DAOFactory generalDAO) {
		this.generalDAO = generalDAO;
	}

	public GeneralDataRepository getGen() {
		return gen;
	}

	public void setGen(GeneralDataRepository gen) {
		this.gen = gen;
	}

	public List<FireStation> getFireStationList() {
		return fireStationList;
	}

	public void setFireStationList(List<FireStation> fireStationList) {
		this.fireStationList = fireStationList;
	}

	private void Instantiate() {
		gen = generalDAO.loadDataFromFile();
		fireStationList = gen.getFirestations();
	}

	@Override
	public Boolean save(FireStation t) {
		if (gen == null)
			Instantiate();

		fireStationList.add(t);
		return true;
	}

	@Override
	public Boolean delete(FireStation t) {
		if (gen == null)
			Instantiate();

		return fireStationList.remove(t);
	}

	@Override
	public FireStation update(int i, FireStation t) {
		if (gen == null)
			Instantiate();

		return fireStationList.set(i, t);
	}

	@Override
	public List<FireStation> getAllData() {
		if (gen == null)
			Instantiate();

		return fireStationList;
	}

}
