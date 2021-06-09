package com.safety.testone.testoneofsafetynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.model.MedicalRecord;
import com.safety.testone.testoneofsafetynet.model.Person;
import com.safety.testone.testoneofsafetynet.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

	@Autowired
	MedicalRecordRepository medicalRecordRepository;

	public MedicalRecordService() {
		super();
	}

	public Iterable<MedicalRecord> getAllMedicalRecords() {

		return medicalRecordRepository.getAllMedicalRecords();
	}

	public MedicalRecord saveANewMedicalRecord(MedicalRecord medRec) {
		return medicalRecordRepository.save(medRec);
	}

	public MedicalRecord deleteAMedicalFile(String firstName, String thelastName) {

		return medicalRecordRepository.deleteAGivenFile(firstName, thelastName);
	}

	public MedicalRecord updateAMedicalFile(MedicalRecord medRec) {
		// TODO Auto-generated method stub
		return medicalRecordRepository.updateAFile(medRec);
	}

}
