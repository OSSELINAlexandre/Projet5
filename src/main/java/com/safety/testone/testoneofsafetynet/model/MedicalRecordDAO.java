package com.safety.testone.testoneofsafetynet.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordDAO {
	
	@Autowired
	General gen;

	@Autowired
	generalDAO genDAO;

	public MedicalRecordDAO() {
		super();
	}
	
	public List<Medicalrecords> getAllMedicalRecords() {

		gen = genDAO.loadDataFromFile();

		List<Medicalrecords> result = gen.getMedicalrecords();

		return result;

	}
	

}
