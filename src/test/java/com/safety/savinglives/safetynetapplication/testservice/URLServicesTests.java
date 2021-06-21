package com.safety.savinglives.safetynetapplication.testservice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safety.savinglives.safetynetapplication.DTO.ChildAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.ChildInHouseAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.CommunityEmailDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireDTO;
import com.safety.savinglives.safetynetapplication.DTO.FirePersonDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireStationDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireStationGeneralDTO;
import com.safety.savinglives.safetynetapplication.DTO.FloodDTO;
import com.safety.savinglives.safetynetapplication.DTO.PersonInfoDTO;
import com.safety.savinglives.safetynetapplication.DTO.PhoneAlertDTO;
import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.repository.FireStationRepository;
import com.safety.savinglives.safetynetapplication.repository.MedicalRecordRepository;
import com.safety.savinglives.safetynetapplication.repository.PersonRepository;
import com.safety.savinglives.safetynetapplication.service.FireStationServices;
import com.safety.savinglives.safetynetapplication.service.MedicalRecordServices;
import com.safety.savinglives.safetynetapplication.service.PersonServices;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class URLServicesTests {

	@Mock
	static FireStationRepository fireStationRepository;

	@Mock
	static MedicalRecordRepository medicalRecordRepository;

	@Mock
	static PersonRepository personRepository;

	@Autowired
	FireStationServices fireStationServices;

	@Autowired
	MedicalRecordServices medicalRecordServices;

	@Autowired
	PersonServices personServices;

	@Autowired
	PersonRepository personRepo;

	@Autowired
	FireStationRepository fsRepository;

	static ArrayList<FireStation> mockUpFireStation = new ArrayList<FireStation>();
	static ArrayList<Person> mockUpPerson = new ArrayList<Person>();
	static ArrayList<MedicalRecord> mockUpMedicalRecords = new ArrayList<MedicalRecord>();

	static {

		mockUpFireStation.add(new FireStation("32 rue du chemin", "1"));
		mockUpFireStation.add(new FireStation("1 rue de la paix", "2"));
		mockUpFireStation.add(new FireStation("78 bis avenue de la lumière", "1"));
		mockUpFireStation.add(new FireStation("Rue général lousin", "2"));

		mockUpPerson.add(new Person("Alex", "Osselin", "32 rue du chemin", "Paris", "54985", "888-888-888",
				"codeurjava@gmail.com"));
		mockUpPerson.add(new Person("Bernard", "Arnaud", "1 rue de la paix", "Paris", "75000", "888-888-888",
				"bernard.arnaud@lvmh.com"));
		mockUpPerson.add(new Person("Sophie", "Lecomte", "78 bis avenue de la lumière", "Paris", "54985", "888-789-888",
				"sophie.Lecomte@gmail.com"));
		mockUpPerson.add(new Person("Christine", "Cain", "Rue général lousin", "Paris", "75000", "765-888-888",
				"cain.cain@gmail.com"));

		List<String> meds = new ArrayList<>();
		meds.add("Dolipranne : 200mg");
		List<String> allergies = new ArrayList<>();
		allergies.add("Pollen");
		mockUpMedicalRecords.add(new MedicalRecord("Alex", "Osselin", "11/06/1995", meds, allergies));
		mockUpMedicalRecords.add(new MedicalRecord("Bernard", "Arnaud", "01/06/1955", meds, allergies));
		mockUpMedicalRecords.add(new MedicalRecord("Sophie", "Lecomte", "05/06/2015", meds, allergies));
		mockUpMedicalRecords.add(new MedicalRecord("Christine", "Cain", "12/28/2007", meds, allergies));
	}

	@Test
	void testgetListOfPeopleCoveredByFireStation() {

		when(fireStationRepository.getAllData()).thenReturn(mockUpFireStation);
		when(medicalRecordRepository.getAllData()).thenReturn(mockUpMedicalRecords);
		when(personRepository.getAllData()).thenReturn(mockUpPerson);
		fireStationServices.setFireStationRepository(fireStationRepository);
		fireStationServices.setPersonRepository(personRepository);
		fireStationServices.setMedicalRecordRepository(medicalRecordRepository);

		FireStationDTO itemA = new FireStationDTO("Alex", "Osselin", "32 rue du chemin", "888-888-888");
		FireStationDTO itemB = new FireStationDTO("Sophie", "Lecomte", "78 bis avenue de la lumière", "888-789-888");
		List<FireStationDTO> listing = new ArrayList<FireStationDTO>();
		listing.add(itemA);
		listing.add(itemB);

		FireStationGeneralDTO expected = new FireStationGeneralDTO(listing, 1, 1);

		FireStationGeneralDTO actual = fireStationServices.getListOfPeopleCoveredByFireStation("1");

		assertEquals(expected, actual);

	}

	@Test
	void testgetListOfChildBasedOnAddress() {

		when(medicalRecordRepository.getAllData()).thenReturn(mockUpMedicalRecords);
		when(personRepository.getAllData()).thenReturn(mockUpPerson);
		personServices.setPersonRepository(personRepository);
		personServices.setMedicalRecordRepository(medicalRecordRepository);

		ChildInHouseAlertDTO childHouse = new ChildInHouseAlertDTO("Sophie", "Lecomte", "6");
		ArrayList<ChildInHouseAlertDTO> childenInHouse = new ArrayList<>();
		childenInHouse.add(childHouse);
		ArrayList<Person> adultInHouse = new ArrayList<>();

		ChildAlertDTO expected = new ChildAlertDTO(childenInHouse, adultInHouse);
		ChildAlertDTO actual = personServices.getListOfChildBasedOnAddress("78 bis avenue de la lumière");
		assertEquals(expected, actual);

	}

	@Test
	void testgetListOfPhoneNumberOfPeopleLivingCloseToTheFireStation() {

		when(fireStationRepository.getAllData()).thenReturn(mockUpFireStation);
		when(personRepository.getAllData()).thenReturn(mockUpPerson);
		fireStationServices.setFireStationRepository(fireStationRepository);
		fireStationServices.setPersonRepository(personRepository);

		PhoneAlertDTO phoneAlerta = new PhoneAlertDTO("888-888-888");
		PhoneAlertDTO phoneAlertb = new PhoneAlertDTO("765-888-888");

		ArrayList<PhoneAlertDTO> expected = new ArrayList<>();
		expected.add(phoneAlerta);
		expected.add(phoneAlertb);

		List<PhoneAlertDTO> actual = fireStationServices.getListOfPhoneNumberOfPeopleLivingCloseToTheFireStation("2");

		assertEquals(actual.hashCode(), expected.hashCode());

	}

	@Test
	void testgetListOfInhabitantAndPhoneNumberOfFireStationCloseBy() {
		when(medicalRecordRepository.getAllData()).thenReturn(mockUpMedicalRecords);
		when(personRepository.getAllData()).thenReturn(mockUpPerson);
		fireStationServices.setPersonRepository(personRepository);
		fireStationServices.setMedicalRecordRepository(medicalRecordRepository);

		List<String> meds = new ArrayList<>();
		meds.add("Dolipranne : 200mg");
		meds.add("Pollen");

		FirePersonDTO expectedItem = new FirePersonDTO("Christine", "Cain", "765-888-888", "13", meds);
		List<FirePersonDTO> expectedList = new ArrayList<>();
		expectedList.add(expectedItem);
		FireDTO expected = new FireDTO(expectedList, "2");
		FireDTO actual = fireStationServices
				.getListOfInhabitantAndPhoneNumberOfFireStationCloseBy("Rue général lousin");

		assertEquals(expected.hashCode(), actual.hashCode());
	}

	@Test
	void testgetListOfAllAddressProtectedByTheFireStation() {
		when(fireStationRepository.getAllData()).thenReturn(mockUpFireStation);
		when(medicalRecordRepository.getAllData()).thenReturn(mockUpMedicalRecords);
		when(personRepository.getAllData()).thenReturn(mockUpPerson);
		fireStationServices.setFireStationRepository(fireStationRepository);
		fireStationServices.setPersonRepository(personRepository);
		fireStationServices.setMedicalRecordRepository(medicalRecordRepository);

		List<String> meds = new ArrayList<>();
		meds.add("Dolipranne : 200mg");
		meds.add("Pollen");

		FloodDTO itemA = new FloodDTO("32 rue du chemin", "Alex", "Osselin", "888-888-888", "25", meds);
		FloodDTO itemB = new FloodDTO("78 bis avenue de la lumière", "Sophie", "Lecomte", "888-789-888", "6", meds);

		List<FloodDTO> expected = new ArrayList<>();
		expected.add(itemA);
		expected.add(itemB);

		List<String> idstation = new ArrayList<>();
		idstation.add("1");

		List<FloodDTO> result = fireStationServices.getListOfAllAddressProtectedByTheFireStation(idstation);

		assertEquals(expected.hashCode(), result.hashCode());

	}

	@Test
	void testgetAllEmailFromAllInhabitantOfCity() {

		when(personRepository.getAllData()).thenReturn(mockUpPerson);
		personServices.setPersonRepository(personRepository);

		CommunityEmailDTO itemA = new CommunityEmailDTO("codeurjava@gmail.com");
		CommunityEmailDTO itemB = new CommunityEmailDTO("bernard.arnaud@lvmh.com");
		CommunityEmailDTO itemC = new CommunityEmailDTO("sophie.Lecomte@gmail.com");
		CommunityEmailDTO itemD = new CommunityEmailDTO("cain.cain@gmail.com");

		List<CommunityEmailDTO> expected = new ArrayList<>();
		expected.add(itemA);
		expected.add(itemB);
		expected.add(itemC);
		expected.add(itemD);

		List<CommunityEmailDTO> actual = personServices.getAllEmailFromAllInhabitantOfCity("Paris");

		assertEquals(expected.hashCode(), actual.hashCode());
	}

	@Test
	void getMedicalInformationOfPeople() {

		when(medicalRecordRepository.getAllData()).thenReturn(mockUpMedicalRecords);
		when(personRepository.getAllData()).thenReturn(mockUpPerson);
		personServices.setPersonRepository(personRepository);
		personServices.setMedicalRecordRepository(medicalRecordRepository);

		List<String> meds = new ArrayList<>();
		meds.add("Dolipranne : 200mg");
		meds.add("Pollen");

		PersonInfoDTO itema = new PersonInfoDTO("Osselin", "Alex", "32 rue du chemin", "25", "codeurjava@gmail.com",
				meds);
		List<PersonInfoDTO> expectedA = new ArrayList<>();
		expectedA.add(itema);

		List<PersonInfoDTO> result = personServices.getMedicalInformationOfPeople("Alex", "Osselin");
		assertEquals(result.hashCode(), expectedA.hashCode());

	}

	@Test
	public void testSave_Person() {

		Person aTester = new Person("John", "Lennox", "Irlande", "Cambridge", "77777", "888-888-888",
				"math@physics.com");
		personServices.setPersonRepository(personRepo);
		Boolean actual = personServices.saveANewPerson(aTester);

		assertEquals(true, actual);

	}

	@Test
	public void testSave_FireStation() {

		FireStation fireStationTester = new FireStation("5", "25698 street road");
		fireStationServices.setFireStationRepository(fsRepository);
		Boolean actual = fireStationServices.saveANewStation(fireStationTester);

		assertEquals(true, actual);

	}
}
