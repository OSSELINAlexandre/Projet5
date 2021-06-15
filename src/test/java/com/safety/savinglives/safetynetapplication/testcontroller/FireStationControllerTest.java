package com.safety.savinglives.safetynetapplication.testcontroller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.savinglives.safetynetapplication.DTO.fireDTO;
import com.safety.savinglives.safetynetapplication.DTO.fireStationDTO;
import com.safety.savinglives.safetynetapplication.DTO.fireStationGeneralDTO;
import com.safety.savinglives.safetynetapplication.DTO.floodDTO;
import com.safety.savinglives.safetynetapplication.DTO.phoneAlertDTO;
import com.safety.savinglives.safetynetapplication.controller.FireStationController;
import com.safety.savinglives.safetynetapplication.controller.PersonController;
import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.repository.FireStationRepository;
import com.safety.savinglives.safetynetapplication.service.FireStationService;
import com.safety.savinglives.safetynetapplication.service.URLService;

@SpringBootTest
@AutoConfigureMockMvc
class FireStationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private URLService urlService;

	@Mock
	private FireStationService firestationservice;

	@Autowired
	private FireStationController firestationcontroller;
	
	@Mock
	private FireStationRepository firestationrepo;

	@Test
	public void testdeleteFireStation_Shouldsend404_WhenFireStationDoesNotExists() throws Exception {

		when(firestationservice.deleteANewStation("Light street")).thenReturn(false);
		firestationcontroller.setFireStationService(firestationservice);
		mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/{address}", "Light street"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testdeleteFireStation_Shouldsend200_WhenFireStationExists() throws Exception {

		when(firestationservice.deleteANewStation("Light street")).thenReturn(true);
		firestationcontroller.setFireStationService(firestationservice);

		mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/{address}", "Light street"))
				.andExpect(status().isOk());

	}

	@Test
	public void testpostANewFireStation_ShouldSend404_ifIsNotOfClassFireStation() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/firestation").content(asJsonString("Alex"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}

	@Test
	public void testpostANewFireStation_ShouldSend200_ifIsOfClassFireStation() throws Exception {

		FireStation testItem = new FireStation();

		when(firestationservice.saveANewStation(testItem)).thenReturn(true);
		firestationcontroller.setFireStationService(firestationservice);

		mockMvc.perform(MockMvcRequestBuilders.post("/firestation").content(asJsonString(testItem))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testUpdateAFireStation_Shouldsend200_WhenFireStationExistInJson() throws Exception {

		FireStation testResult = new FireStation();

		when(firestationservice.updateAStation("Light street", "1")).thenReturn(testResult);
		firestationcontroller.setFireStationService(firestationservice);

		mockMvc.perform(MockMvcRequestBuilders.put("/firestation/{address}/{newId}", "Light street", "1"))
				.andExpect(status().isOk());

	}

	@Test
	public void testUpdateAFireStation_Shouldsend404_WhenFireStationDoesNotExistInJson() throws Exception {

		when(firestationrepo.getAllData()).thenReturn(null);
		when(firestationservice.updateAStation("Light street", "1")).thenReturn(null);
		firestationcontroller.setFireStationService(firestationservice);

		mockMvc.perform(MockMvcRequestBuilders.put("/firestation/{address}/{newId}", "Light street", "1"))
				.andExpect(status().isNotFound());

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
		firestationcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber={station_number}", "1"))
				.andExpect(status().isOk());

	}

	@Test
	public void testListOfListCoverageByStationNumber_shouldRetrun404_WhenValueDoesNotExistInJson() throws Exception {

		List<fireStationDTO> listing = new ArrayList<fireStationDTO>();

		fireStationGeneralDTO testItem = new fireStationGeneralDTO(listing, 0, 0);

		when(urlService.getListOfPeopleCoveredByFireStation("1")).thenReturn(testItem);
		firestationcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber={station_number}", "1"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation_ShouldReturn404_WhenFireStationDoesNotExist()
			throws Exception {

		ArrayList<phoneAlertDTO> testItem = new ArrayList<>();
		when(urlService.getListOfPhoneNumberOfPeopleLivingCloseToTheFireStation("2")).thenReturn(testItem);
		firestationcontroller.setUrlService(urlService);

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
		firestationcontroller.setUrlService(urlService);

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
		firestationcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/fire?address={address}", "rue lumière"))
				.andExpect(status().isOk());

	}

	@Test
	public void testGetListOfInhabitantAndPhoneNumberOfFireStationCloseBy_ShouldReturn404_WhenAddressOfPersonDoesNOTExist()
			throws Exception {
		List<fireDTO> testItem = new ArrayList<>();

		when(urlService.getListOfInhabitantAndPhoneNumberOfFireStationCloseBy("rue lumière")).thenReturn(testItem);
		firestationcontroller.setUrlService(urlService);

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
		firestationcontroller.setUrlService(urlService);

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
		firestationcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations?stations={stations}", "1,2"))
				.andExpect(status().isOk());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
