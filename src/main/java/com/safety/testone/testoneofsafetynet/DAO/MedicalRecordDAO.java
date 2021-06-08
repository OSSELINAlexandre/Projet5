package com.safety.testone.testoneofsafetynet.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safety.testone.testoneofsafetynet.model.MedicalRecord;
import com.safety.testone.testoneofsafetynet.model.Person;
import com.safety.testone.testoneofsafetynet.repository.GeneralData;
import com.safety.testone.testoneofsafetynet.repository.generalDataDAO;

@Component
public class MedicalRecordDAO {

	@Autowired
	private generalDataDAO generalDAO;
	
	private GeneralData gen;
	private List<MedicalRecord> medicalRecordList;

	public MedicalRecordDAO() {
		super();
	}

	public List<MedicalRecord> getAllMedicalRecords() {
		if (gen == null)
			Instantiate();

		return medicalRecordList;

	}

	private void Instantiate() {

		gen = generalDAO.loadDataFromFile();
		medicalRecordList = gen.getMedicalrecords();

	}

	public MedicalRecord save(MedicalRecord medRec) {
		if (gen == null)
			Instantiate();

		medicalRecordList.add(medRec);
	
		return medRec;
			
	}

	public MedicalRecord deleteAGivenFile(String firstName, String thelastName) {
		if (gen == null)
			Instantiate();

		for (MedicalRecord medred : medicalRecordList) {

			if (medred.getFirstName().equals(firstName) && medred.getLastName().equals(thelastName)) {

				MedicalRecord deletedFile = medred;
				medicalRecordList.remove(medred);
				return deletedFile;
			}
		}

		return null;
	}

	public MedicalRecord updateAFile(MedicalRecord medRecords) {
		if (gen == null)
			Instantiate();

		for (int i = 0; i < medicalRecordList.size(); i++) {

			if (medicalRecordList.get(i).getFirstName().equals(medRecords.getFirstName())
					&& medicalRecordList.get(i).getLastName().equals(medRecords.getLastName())) {

				medicalRecordList.set(i, medRecords);
				return medRecords;
			}
		}
		return null;
	}



}
