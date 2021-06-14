package com.safety.savinglives.safetynetapplication.testservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safety.savinglives.safetynetapplication.DTO.FireStationDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireStationGeneralDTO;
import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.repository.FireStationRepository;
import com.safety.savinglives.safetynetapplication.repository.MedicalRecordRepository;
import com.safety.savinglives.safetynetapplication.repository.PersonRepository;
import com.safety.savinglives.safetynetapplication.service.MedicalRecordService;
import com.safety.savinglives.safetynetapplication.service.PersonService;
import com.safety.savinglives.safetynetapplication.service.URLService;

@SpringBootTest
class URLServiceTest {

	@Autowri
	@Mock
	static FireStationRepository fireStationRepository;

	@Mock
	static MedicalRecordRepository medicalRecordRepository;

	@Mock
	static PersonRepository personRepository;

	@Autowired
	URLService urlservice;

	static ArrayList<FireStation> mockUpFireStation = new ArrayList<FireStation>();
	static ArrayList<Person> mockUpPerson = new ArrayList<Person>();
	static ArrayList<MedicalRecord> mockUpMedicalRecords = new ArrayList<MedicalRecord>();

	static {

		mockUpFireStation.add(new FireStation("32 rue du chemin", "1"));
		mockUpFireStation.add(new FireStation("1 rue de la paix", "2"));
		mockUpFireStation.add(new FireStation("78 bis avenue de la lumière", "1"));
		mockUpFireStation.add(new FireStation("Rue général lousin", "2"));

		mockUpPerson.add(new Person("Alex", "Osselin", "32 rue du chemin", "Meaux", "54985", "888-888-888",
				"codeurjava@gmail.com"));
		mockUpPerson.add(new Person("Bernard", "Arnaud", "1 rue de la paix", "Paris", "75000", "888-888-888",
				"bernard.arnaud@lvmh.com"));
		mockUpPerson.add(new Person("Sophie", "Lecomte", "78 bis avenue de la lumière", "Meaux", "54985", "888-789-888",
				"sophie.Lecomte@gmail.com"));
		mockUpPerson.add(new Person("Christine", "Cain", "Rue général lousin", "Paris", "75000", "765-888-888",
				"cain.cain@gmail.com"));

		mockUpMedicalRecords.add(new MedicalRecord("Alex", "Osselin", "11/06/1995", null, null));
		mockUpMedicalRecords.add(new MedicalRecord("Bernard", "Arnaud", "01/06/1955", null, null));
		mockUpMedicalRecords.add(new MedicalRecord("Sophie", "Lecomte", "05/06/2000", null, null));
		mockUpMedicalRecords.add(new MedicalRecord("Christine", "Cain", "28/12/2007", null, null));
	}

	@BeforeAll
	private static void init() {
		when(fireStationRepository.getAllData()).thenReturn(mockUpFireStation);
		when(medicalRecordRepository.getAllData()).thenReturn(mockUpMedicalRecords);
		when(personRepository.getAllData()).thenReturn(mockUpPerson);
	}

	@Test
	void testgetListOfPeopleCoveredByFireStation() {

		FireStationDTO itemA = new FireStationDTO("Alex", "Osselin", "32 rue du chemin", "888-888-888");
		FireStationDTO itemB = new FireStationDTO("Sophie", "Lecomte", "78 bis avenue de la lumière", "888-789-888");
		List<FireStationDTO> listing = new ArrayList<FireStationDTO>();
		listing.add(itemB);
		listing.add(itemA);

		FireStationGeneralDTO expected = new FireStationGeneralDTO(listing, 1, 1);

		FireStationGeneralDTO actual = urlservice.getListOfPeopleCoveredByFireStation("1");

		assertEquals(expected, actual);

	}

	@Test
	void testgetListOfChildBasedOnAddress() {
		fail("Not yet implemented");
	}

	@Test
	void testgetListOfPhoneNumberOfPeopleLivingCloseToTheFireStation() {
		fail("Not yet implemented");
	}

	@Test
	void testgetListOfInhabitantAndPhoneNumberOfFireStationCloseBy() {
		fail("Not yet implemented");
	}

	@Test
	void testgetListOfAllAddressProtectedByTheFireStation() {
		fail("Not yet implemented");
	}

	@Test
	void getAllEmailFromAllInhabitantOfCity() {
		fail("Not yet implemented");
	}

}
