package com.safety.testone.testoneofsafetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safety.testone.testoneofsafetynet.model.MedicalRecord;
import com.safety.testone.testoneofsafetynet.model.Person;
import com.safety.testone.testoneofsafetynet.service.MedicalRecordService;

@RestController
public class MedicalRecordsController {

	@Autowired
	MedicalRecordService medicalRecordService;

	@GetMapping(value = "/medicalrecords")
	public Iterable<MedicalRecord> getAllRecords() {

		return medicalRecordService.getAllMedicalRecords();
	}

	@PostMapping(value = "/medicalrecords")
	public MedicalRecord postANewMedicalRecord(@RequestBody MedicalRecord MedRec) {
		return medicalRecordService.saveANewMedicalRecord(MedRec);
	}

	@DeleteMapping(value = "/medicalrecords/{firstName}/{thelastName}")
	public MedicalRecord createAPerson(@PathVariable("firstName") String firstName,
			@PathVariable("thelastName") String thelastName) {

		return medicalRecordService.deleteAMedicalFile(firstName, thelastName);

	}

	@PutMapping(value = "/medicalrecords")
	public MedicalRecord updateAPerson(@RequestBody MedicalRecord medRec) {

		return medicalRecordService.updateAMedicalFile(medRec);
	}

}
