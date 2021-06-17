package com.safety.savinglives.safetynetapplication.testservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.service.FireStationServices;
import com.safety.savinglives.safetynetapplication.service.MedicalRecordServices;
import com.safety.savinglives.safetynetapplication.service.PersonServices;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EndpointsServiceTests {

	@Autowired
	FireStationServices fireStationServices;

	@Autowired
	MedicalRecordServices medicalRecordServices;

	@Autowired
	PersonServices personServices;

	private MedicalRecord medicalRecordTester;
	private FireStation fireStationTester;
	private Person personTester;

	@BeforeEach
	public void initiliazing() {
		List<String> medications = new ArrayList<>();
		medications.add("Miserable:100mg");
		List<String> allergies = new ArrayList<>();
		allergies.add("iniquity");
		medicalRecordTester = new MedicalRecord("Victor", "Hugo", "05/22/1885", medications, allergies);
		fireStationTester = new FireStation("Light covenant street", "3");

		personTester = new Person("C.S", "Lewis", "Narnia", "Cupboard", "77777", "888-888-888", "narnia@kingdom.com");

	}

	@Test
	public void testSave_MedicalRecord() {

		Boolean actual = medicalRecordServices.saveANewMedicalRecord(medicalRecordTester);

		assertEquals(true, actual);

	}

	@Test
	public void testDelete_MedicalRecord() {

		Boolean testItem = medicalRecordServices.deleteAMedicalFile(medicalRecordTester.getFirstName(),
				medicalRecordTester.getLastName());

		assertTrue(testItem == false);
	}

	@Test
	public void testUpdate_MedicalRecord() {

		MedicalRecord testItem = medicalRecordServices.updateAMedicalFile(medicalRecordTester);

		assertTrue(testItem == null);

	}

	@Test
	public void testSave_FireStation() {

		Boolean actual = fireStationServices.saveANewStation(fireStationTester);

		assertEquals(true, actual);

	}

	@Test
	public void testDelete_FireStation() {

		Boolean testItem = fireStationServices.deleteANewStation(fireStationTester.getAddress());

		assertTrue(testItem == false);
	}

	@Test
	public void testUpdate_FireStation() {

		FireStation testItem = fireStationServices.updateAStation(fireStationTester.getAddress(), "5");

		assertTrue(testItem == null);

	}

	@Test
	public void testSave_Person() {

		Boolean actual = personServices.saveANewPerson(personTester);

		assertEquals(true, actual);

	}

	@Test
	public void testDelete_Person() {
		personTester.setFirstName("Mickey");

		Boolean testItem = personServices.deleteAPerson(personTester.getFirstName(), personTester.getLastName());

		assertTrue(testItem == false);
	}

	@Test
	public void testUpdate_Person() {

		personTester.setFirstName("Tolkien");
		Person testItem = personServices.updateAPerson(personTester);

		assertTrue(testItem == null);

	}

}
