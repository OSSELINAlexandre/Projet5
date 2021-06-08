package com.safety.testone.testoneofsafetynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.DAO.MedicalRecordDAO;
import com.safety.testone.testoneofsafetynet.model.MedicalRecord;
import com.safety.testone.testoneofsafetynet.model.Person;

@Service
public class MedicalRecordService {

	@Autowired
	MedicalRecordDAO medicalRecordDAO;

	public MedicalRecordService() {
		super();
	}

	public Iterable<MedicalRecord> getAllMedicalRecords() {

		return medicalRecordDAO.getAllMedicalRecords();
	}

	public MedicalRecord saveANewMedicalRecord(MedicalRecord medRec) {
		return medicalRecordDAO.save(medRec);
	}

	public MedicalRecord deleteAMedicalFile(String firstName, String thelastName) {

		return medicalRecordDAO.deleteAGivenFile(firstName, thelastName);
	}

	public MedicalRecord updateAMedicalFile(MedicalRecord medRec) {
		// TODO Auto-generated method stub
		return medicalRecordDAO.updateAFile(medRec);
	}

}
