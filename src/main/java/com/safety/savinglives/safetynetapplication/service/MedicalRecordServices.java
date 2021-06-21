package com.safety.savinglives.safetynetapplication.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.repository.MedicalRecordRepository;

@Service
public class MedicalRecordServices {

	private static final Logger logger = LogManager.getLogger(MedicalRecordServices.class);

	@Autowired
	MedicalRecordRepository medicalRecordRepository;

	public MedicalRecordServices() {
		super();
	}

	public Iterable<MedicalRecord> getAllMedicalRecords() {

		return medicalRecordRepository.getAllData();
	}

	public Boolean saveANewMedicalRecord(MedicalRecord medRec) {
		logger.debug("Testing Environment");

		return medicalRecordRepository.save(medRec);
	}

	public Boolean deleteAMedicalFile(String firstName, String thelastName) {

		List<MedicalRecord> copyOfRealList = medicalRecordRepository.getAllData();

		for (MedicalRecord medred : copyOfRealList) {

			if (medred.getFirstName().equals(firstName) && medred.getLastName().equals(thelastName)) {

				return medicalRecordRepository.delete(medred);
			}
		}

		return false;
	}

	public MedicalRecord updateAMedicalFile(MedicalRecord medRec) {

		List<MedicalRecord> copyOfRealList = medicalRecordRepository.getAllData();

		for (int i = 0; i < copyOfRealList.size(); i++) {

			if (copyOfRealList.get(i).getFirstName().equals(medRec.getFirstName())
					&& copyOfRealList.get(i).getLastName().equals(medRec.getLastName())) {
				return medicalRecordRepository.update(i, medRec);
			}
		}
		return null;
	}
}
