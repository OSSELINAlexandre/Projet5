package com.safety.savinglives.safetynetapplication.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.model.Person;

@Component
public class MedicalRecordRepository implements DAOMethods<MedicalRecord> {

	@Autowired
	private DAOFactory generalDAO;

	private GeneralDataRepository gen;
	private List<MedicalRecord> medicalRecordList;

	public MedicalRecordRepository() {
		super();
	}

	private void Instantiate() {

		gen = generalDAO.loadDataFromFile();
		medicalRecordList = gen.getMedicalrecords();

	}

	@Override
	public Boolean delete(MedicalRecord t) {
		if (gen == null)
			Instantiate();

		return medicalRecordList.remove(t);
	}

	@Override
	public MedicalRecord update(int i, MedicalRecord t) {
		if (gen == null) {
			Instantiate();
		}

		return medicalRecordList.set(i, t);

	}

	@Override
	public Boolean save(MedicalRecord medRec) {
		if (gen == null)
			Instantiate();

		if (medicalRecordList.add(medRec)) {
			return true;
		}

		return false;

	}

	@Override
	public List<MedicalRecord> getAllData() {
		if (gen == null)
			Instantiate();

		return medicalRecordList;
	}

}
