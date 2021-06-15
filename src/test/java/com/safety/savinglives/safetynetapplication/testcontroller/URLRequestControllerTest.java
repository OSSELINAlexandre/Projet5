package com.safety.savinglives.safetynetapplication.testcontroller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safety.savinglives.safetynetapplication.DTO.ChildAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.ChildInHouseAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.CommunityEmailDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireStationDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireStationGeneralDTO;
import com.safety.savinglives.safetynetapplication.DTO.FloodDTO;
import com.safety.savinglives.safetynetapplication.DTO.PersonInfoDTO;
import com.safety.savinglives.safetynetapplication.DTO.PhoneAlertDTO;
import com.safety.savinglives.safetynetapplication.controller.URLRequestController;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.service.URLService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class URLRequestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private URLService urlService;

	@Autowired
	URLRequestController urlcontroller;

	@Test
	public void testListOfChildLivingInTheAdress_ShouldReturn404_WhenAdressDoesNotContainChildren() throws Exception {

		ChildAlertDTO testItem = new ChildAlertDTO(new ArrayList<ChildInHouseAlertDTO>(), new ArrayList<Person>());
		when(urlService.getListOfChildBasedOnAddress("32 rue du chemin")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert?address={adress}", "32 rue du chemin"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testListOfChildLivingInTheAdress_ShouldReturn200_WhenAdressDoContainChildren() throws Exception {

		ChildInHouseAlertDTO childHouse = new ChildInHouseAlertDTO("Sophie", "Lecomte", "6");
		ArrayList<ChildInHouseAlertDTO> childenInHouse = new ArrayList<>();
		childenInHouse.add(childHouse);
		ArrayList<Person> adultInHouse = new ArrayList<>();

		ChildAlertDTO testItem = new ChildAlertDTO(childenInHouse, adultInHouse);

		when(urlService.getListOfChildBasedOnAddress("32 rue du chemin")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert?address={adress}", "32 rue du chemin"))
				.andExpect(status().isOk());

	}

	@Test
	public void testListCoverageByStationNumber_shouldRetrun200_WhenValueExistInJson() throws Exception {

		FireStationDTO itemA = new FireStationDTO("Alex", "Osselin", "32 rue du chemin", "888-888-888");
		FireStationDTO itemB = new FireStationDTO("Sophie", "Lecomte", "78 bis avenue de la lumière", "888-789-888");
		List<FireStationDTO> listing = new ArrayList<FireStationDTO>();
		listing.add(itemA);
		listing.add(itemB);

		FireStationGeneralDTO testItem = new FireStationGeneralDTO(listing, 1, 1);

		when(urlService.getListOfPeopleCoveredByFireStation("1")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber={station_number}", "1"))
				.andExpect(status().isOk());

	}

	@Test
	public void testListOfListCoverageByStationNumber_shouldRetrun404_WhenValueDoesNotExistInJson() throws Exception {

		List<FireStationDTO> listing = new ArrayList<FireStationDTO>();

		FireStationGeneralDTO testItem = new FireStationGeneralDTO(listing, 0, 0);

		when(urlService.getListOfPeopleCoveredByFireStation("1")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber={station_number}", "1"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation_ShouldReturn404_WhenFireStationDoesNotExist()
			throws Exception {

		ArrayList<PhoneAlertDTO> testItem = new ArrayList<>();
		when(urlService.getListOfPhoneNumberOfPeopleLivingCloseToTheFireStation("2")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert?firestation={firestation}", "2"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation_ShouldReturn200_WhenFireStationExist()
			throws Exception {

		PhoneAlertDTO phoneAlerta = new PhoneAlertDTO("888-888-888");
		PhoneAlertDTO phoneAlertb = new PhoneAlertDTO("765-888-888");

		ArrayList<PhoneAlertDTO> testItem = new ArrayList<>();
		testItem.add(phoneAlerta);
		testItem.add(phoneAlertb);

		when(urlService.getListOfPhoneNumberOfPeopleLivingCloseToTheFireStation("2")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert?firestation={firestation}", "2"))
				.andExpect(status().isOk());

	}

	@Test
	public void testGetListOfInhabitantAndPhoneNumberOfFireStationCloseBy_ShouldReturn200_WhenAddressDoesExist()
			throws Exception {

		List<String> meds = new ArrayList<>();
		meds.add("Dolipranne : 200mg");
		meds.add("Pollen");

		FireDTO expectedItem = new FireDTO("Christine", "Cain", "765-888-888", "13", meds);
		List<FireDTO> testItem = new ArrayList<>();
		testItem.add(expectedItem);

		when(urlService.getListOfInhabitantAndPhoneNumberOfFireStationCloseBy("rue lumière")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/fire?address={address}", "rue lumière"))
				.andExpect(status().isOk());

	}

	@Test
	public void testGetListOfInhabitantAndPhoneNumberOfFireStationCloseBy_ShouldReturn404_WhenAddressOfPersonDoesNOTExist()
			throws Exception {
		List<FireDTO> testItem = new ArrayList<>();

		when(urlService.getListOfInhabitantAndPhoneNumberOfFireStationCloseBy("rue lumière")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/fire?address={address}", "rue lumière"))
				.andExpect(status().isNotFound());

	}


	@Test
	public void testGetListOfAllAddressProtectedByTheFireStation_ShouldReturn404_WhenListIsIncorrect()
			throws Exception {

//		List<String> mockPurposes = new ArrayList<>();
//		mockPurposes.add("a");
//		mockPurposes.add("b");
//		mockPurposes.add("c");

		List<String> idstation = new ArrayList<>();
		idstation.add("1");
		idstation.add("2");
		List<FloodDTO> TestItem = new ArrayList<>();

		when(urlService.getListOfAllAddressProtectedByTheFireStation(idstation))
				.thenReturn(TestItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations?stations={stations}", "1,2"))
				.andExpect(status().isNotFound());

	}


	@Test
	public void testGetListOfAllAddressProtectedByTheFireStation_ShouldReturn200_WhenListIsCorrect() throws Exception {

		List<String> meds = new ArrayList<>();
		meds.add("Dolipranne : 200mg");
		meds.add("Pollen");

		FloodDTO itemA = new FloodDTO("32 rue du chemin", "Alex", "Osselin", "888-888-888", "25", meds);
		FloodDTO itemB = new FloodDTO("78 bis avenue de la lumière", "Sophie", "Lecomte", "888-789-888", "6", meds);

		List<FloodDTO> TestItem = new ArrayList<>();
		TestItem.add(itemA);
		TestItem.add(itemB);

		List<String> idstation = new ArrayList<>();
		idstation.add("1");
		idstation.add("2");

		when(urlService.getListOfAllAddressProtectedByTheFireStation(idstation)).thenReturn(TestItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations?stations={stations}", "1,2"))
				.andExpect(status().isOk());

	}

	@Test
	public void testgetMedicalInformationOfPeople_ShouldReturn404_WhenPersonDoesNotExist() throws Exception {

		List<PersonInfoDTO> testItem = new ArrayList<>();

		when(urlService.getMedicalInformationOfPeople("Alex", "Osselin")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName={?}&lastName={?}", "Alex", "Osselin"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testgetMedicalInformationOfPeople_ShouldReturn200_WhenPersonExistInJson() throws Exception {

		List<String> meds = new ArrayList<>();
		meds.add("Dolipranne : 200mg");
		meds.add("Pollen");

		PersonInfoDTO itema = new PersonInfoDTO("Osselin", "Alex", "32 rue du chemin", "25", "codeurjava@gmail.com",
				meds);
		List<PersonInfoDTO> testItem = new ArrayList<>();
		testItem.add(itema);

		when(urlService.getMedicalInformationOfPeople("Alex", "Osselin")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName={Jacob}&lastName={Boyd}", "Alex", "Osselin"))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetAllEmailFromAllInhabitantOfCity_ShouldReturn404_WhenCityIsInccorect() throws Exception {

		List<CommunityEmailDTO> testItem = new ArrayList<>();

		when(urlService.getAllEmailFromAllInhabitantOfCity("Paris")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail?city={city}", "Paris"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetAllEmailFromAllInhabitantOfCity_ShouldReturn200_WhenCityExistInJson() throws Exception {

		CommunityEmailDTO itemA = new CommunityEmailDTO("codeurjava@gmail.com");
		CommunityEmailDTO itemB = new CommunityEmailDTO("bernard.arnaud@lvmh.com");
		CommunityEmailDTO itemC = new CommunityEmailDTO("sophie.Lecomte@gmail.com");
		CommunityEmailDTO itemD = new CommunityEmailDTO("cain.cain@gmail.com");

		List<CommunityEmailDTO> testItem = new ArrayList<>();
		testItem.add(itemA);
		testItem.add(itemB);
		testItem.add(itemC);
		testItem.add(itemD);

		when(urlService.getAllEmailFromAllInhabitantOfCity("Paris")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail?city={city}", "Paris")).andExpect(status().isOk());

	}

}
