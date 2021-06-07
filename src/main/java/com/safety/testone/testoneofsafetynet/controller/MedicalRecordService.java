package com.safety.testone.testoneofsafetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.model.MedicalRecordDAO;
import com.safety.testone.testoneofsafetynet.model.Medicalrecords;
import com.safety.testone.testoneofsafetynet.model.Person;

@Service
public class MedicalRecordService {
	
	@Autowired
	MedicalRecordDAO medicalRecordDAO;
	

	public MedicalRecordService() {
		super();
	}


	public Iterable<Medicalrecords> getAllMedicalRecords() {
		
		return medicalRecordDAO.getAllMedicalRecords();
	}

}
