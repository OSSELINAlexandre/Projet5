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

import com.safety.savinglives.safetynetapplication.DTO.childAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.childInHouseAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.communityEmailDTO;
import com.safety.savinglives.safetynetapplication.DTO.fireDTO;
import com.safety.savinglives.safetynetapplication.DTO.fireStationDTO;
import com.safety.savinglives.safetynetapplication.DTO.fireStationGeneralDTO;
import com.safety.savinglives.safetynetapplication.DTO.floodDTO;
import com.safety.savinglives.safetynetapplication.DTO.personInfoDTO;
import com.safety.savinglives.safetynetapplication.DTO.phoneAlertDTO;
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

		childAlertDTO testItem = new childAlertDTO(new ArrayList<childInHouseAlertDTO>(), new ArrayList<Person>());
		when(urlService.getListOfChildBasedOnAddress("32 rue du chemin")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert?address={adress}", "32 rue du chemin"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testListOfChildLivingInTheAdress_ShouldReturn200_WhenAdressDoContainChildren() throws Exception {

		childInHouseAlertDTO childHouse = new childInHouseAlertDTO("Sophie", "Lecomte", "6");
		ArrayList<childInHouseAlertDTO> childenInHouse = new ArrayList<>();
		childenInHouse.add(childHouse);
		ArrayList<Person> adultInHouse = new ArrayList<>();

		childAlertDTO testItem = new childAlertDTO(childenInHouse, adultInHouse);

		when(urlService.getListOfChildBasedOnAddress("32 rue du chemin")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert?address={adress}", "32 rue du chemin"))
				.andExpect(status().isOk());

	}

	@Test
	public void testListCoverageByStationNumber_shouldRetrun200_WhenValueExistInJson() throws Exception {

		fireStationDTO itemA = new fireStationDTO("Alex", "Osselin", "32 rue du chemin", "888-888-888");
		fireStationDTO itemB = new fireStationDTO("Sophie", "Lecomte", "78 bis avenue de la lumière", "888-789-888");
		List<fireStationDTO> listing = new ArrayList<fireStationDTO>();
		listing.add(itemA);
		listing.add(itemB);

		fireStationGeneralDTO testItem = new fireStationGeneralDTO(listing, 1, 1);

		when(urlService.getListOfPeopleCoveredByFireStation("1")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber={station_number}", "1"))
				.andExpect(status().isOk());

	}

	@Test
	public void testListOfListCoverageByStationNumber_shouldRetrun404_WhenValueDoesNotExistInJson() throws Exception {

		List<fireStationDTO> listing = new ArrayList<fireStationDTO>();

		fireStationGeneralDTO testItem = new fireStationGeneralDTO(listing, 0, 0);

		when(urlService.getListOfPeopleCoveredByFireStation("1")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber={station_number}", "1"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation_ShouldReturn404_WhenFireStationDoesNotExist()
			throws Exception {

		ArrayList<phoneAlertDTO> testItem = new ArrayList<>();
		when(urlService.getListOfPhoneNumberOfPeopleLivingCloseToTheFireStation("2")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert?firestation={firestation}", "2"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation_ShouldReturn200_WhenFireStationExist()
			throws Exception {

		phoneAlertDTO phoneAlerta = new phoneAlertDTO("888-888-888");
		phoneAlertDTO phoneAlertb = new phoneAlertDTO("765-888-888");

		ArrayList<phoneAlertDTO> testItem = new ArrayList<>();
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

		fireDTO expectedItem = new fireDTO("Christine", "Cain", "765-888-888", "13", meds);
		List<fireDTO> testItem = new ArrayList<>();
		testItem.add(expectedItem);

		when(urlService.getListOfInhabitantAndPhoneNumberOfFireStationCloseBy("rue lumière")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/fire?address={address}", "rue lumière"))
				.andExpect(status().isOk());

	}

	@Test
	public void testGetListOfInhabitantAndPhoneNumberOfFireStationCloseBy_ShouldReturn404_WhenAddressOfPersonDoesNOTExist()
			throws Exception {
		List<fireDTO> testItem = new ArrayList<>();

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
		List<floodDTO> TestItem = new ArrayList<>();

		when(urlService.getListOfAllAddressProtectedByTheFireStation(idstation)).thenReturn(TestItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations?stations={stations}", "1,2"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetListOfAllAddressProtectedByTheFireStation_ShouldReturn200_WhenListIsCorrect() throws Exception {

		List<String> meds = new ArrayList<>();
		meds.add("Dolipranne : 200mg");
		meds.add("Pollen");

		floodDTO itemA = new floodDTO("32 rue du chemin", "Alex", "Osselin", "888-888-888", "25", meds);
		floodDTO itemB = new floodDTO("78 bis avenue de la lumière", "Sophie", "Lecomte", "888-789-888", "6", meds);

		List<floodDTO> TestItem = new ArrayList<>();
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

		List<personInfoDTO> testItem = new ArrayList<>();

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

		personInfoDTO itema = new personInfoDTO("Osselin", "Alex", "32 rue du chemin", "25", "codeurjava@gmail.com",
				meds);
		List<personInfoDTO> testItem = new ArrayList<>();
		testItem.add(itema);

		when(urlService.getMedicalInformationOfPeople("Alex", "Osselin")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName={Jacob}&lastName={Boyd}", "Alex", "Osselin"))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetAllEmailFromAllInhabitantOfCity_ShouldReturn404_WhenCityIsInccorect() throws Exception {

		List<communityEmailDTO> testItem = new ArrayList<>();

		when(urlService.getAllEmailFromAllInhabitantOfCity("Paris")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail?city={city}", "Paris"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetAllEmailFromAllInhabitantOfCity_ShouldReturn200_WhenCityExistInJson() throws Exception {

		communityEmailDTO itemA = new communityEmailDTO("codeurjava@gmail.com");
		communityEmailDTO itemB = new communityEmailDTO("bernard.arnaud@lvmh.com");
		communityEmailDTO itemC = new communityEmailDTO("sophie.Lecomte@gmail.com");
		communityEmailDTO itemD = new communityEmailDTO("cain.cain@gmail.com");

		List<communityEmailDTO> testItem = new ArrayList<>();
		testItem.add(itemA);
		testItem.add(itemB);
		testItem.add(itemC);
		testItem.add(itemD);

		when(urlService.getAllEmailFromAllInhabitantOfCity("Paris")).thenReturn(testItem);
		urlcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail?city={city}", "Paris")).andExpect(status().isOk());

	}

}
