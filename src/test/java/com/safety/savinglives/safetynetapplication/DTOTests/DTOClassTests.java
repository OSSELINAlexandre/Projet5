package com.safety.savinglives.safetynetapplication.DTOTests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.safety.savinglives.safetynetapplication.DTO.ChildAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.ChildInHouseAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.CommunityEmailDTO;
import com.safety.savinglives.safetynetapplication.DTO.CoveragePersonOfAStationDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireDTO;
import com.safety.savinglives.safetynetapplication.DTO.FirePersonDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireStationDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireStationGeneralDTO;
import com.safety.savinglives.safetynetapplication.DTO.FloodDTO;
import com.safety.savinglives.safetynetapplication.DTO.PersonInfoDTO;
import com.safety.savinglives.safetynetapplication.DTO.PhoneAlertDTO;
import com.safety.savinglives.safetynetapplication.model.Person;

@SpringBootTest
public class DTOClassTests {

	@Test
	public void testingChildAlertDTO() {

		List<ChildInHouseAlertDTO> childInTheHouse = new ArrayList<>();
		childInTheHouse.add(new ChildInHouseAlertDTO("Martin", "luther", "37"));
		List<Person> AdultInTheHouse = new ArrayList<>();
		AdultInTheHouse.add(new Person("Abraham", "Lincoln", "Light street road", "Paris", "78654", "888-888-888",
				"javadeveloper@gmail.com"));
		ChildAlertDTO testingItem = new ChildAlertDTO(childInTheHouse, AdultInTheHouse);

		assertTrue(testingItem.getAdultInTheHouse().get(0).getLastName().equals("Lincoln"));
		assertTrue(testingItem.getChildInTheHouse().get(0).getLastName().equals("luther"));

	}

	@Test
	public void testCommunityEmailDTO() {

		CommunityEmailDTO testItem = new CommunityEmailDTO("Javadevloper@gmail.com");

		assertTrue(testItem.getEmail().equals("Javadevloper@gmail.com"));

	}

	@Test
	public void testCoveragePersonOfAStationDTO() {

		CoveragePersonOfAStationDTO testItem = new CoveragePersonOfAStationDTO("7", "Nelson", "Mandela", "Adonai Road",
				"888-888-888");

		assertTrue(testItem.getAdress().equals("Adonai Road"));
		assertTrue(testItem.getLastName().equals("Mandela"));
		assertTrue(testItem.getName().equals("Nelson"));
		assertTrue(testItem.getPhoneNumber().equals("888-888-888"));
		assertTrue(testItem.getStationnumber().equals("7"));
	}

	@Test
	public void testFireDTO() {
		List<FirePersonDTO> citizenLivingAtTheAddress = new ArrayList<>();
		List<String> medRec = new ArrayList<>();
		medRec.add("Pollen");
		medRec.add("Honey");
		FirePersonDTO testingItem = new FirePersonDTO("John", "Smith", "888-888-888", "48", medRec);
		citizenLivingAtTheAddress.add(testingItem);
		FireDTO testItem = new FireDTO(citizenLivingAtTheAddress, "7");

		assertTrue(testItem.getCitizenLivingAtTheAddress().get(0).getLastName().equals("Smith"));
		assertTrue(testItem.getFireStationNumberCoveringThisAddress().equals("7"));

	}

	@Test
	public void testFireStationDTO() {

		FireStationDTO testItem = new FireStationDTO("Isaac", "Newton", "Cambridge University", "888-888-888");

		assertTrue(testItem.getLastName().equals("Newton"));
	}

	@Test
	public void testFireStationGeneralDTO() {

		List<FireStationDTO> listTest = new ArrayList<>();
		FireStationDTO testItem = new FireStationDTO("Isaac", "Newton", "Cambridge University", "888-888-888");
		listTest.add(testItem);

		FireStationGeneralDTO testingItem = new FireStationGeneralDTO(listTest, 0, 0);

		assertTrue(testingItem.getAdultCount() == 0);

	}

	@Test
	public void testFloodDTO() {

		List<String> medRec = new ArrayList<>();
		medRec.add("Pollen");
		medRec.add("Honey");

		FloodDTO testingItem = new FloodDTO("Oxford Choir", "Galileo", "Galileli", "888-888-888", "82", medRec);

		assertTrue(testingItem.getAddress().equals("Oxford Choir"));

	}

	@Test
	public void testPersonInfoDTO() {

		List<String> medRec = new ArrayList<>();
		medRec.add("Pollen");
		medRec.add("Honey");

		PersonInfoDTO testingItem = new PersonInfoDTO("David", "Goliath", "Babel tower", "456", "sling.rock@yahoo.com",
				medRec);

		assertTrue(testingItem.getAge().equals("456"));

	}

	@Test
	public void testPhoneAlertDTO() {

		PhoneAlertDTO testItem = new PhoneAlertDTO("888-888-888");

		assertTrue(testItem.getPhoneNumber().equals("888-888-888"));
	}

}
