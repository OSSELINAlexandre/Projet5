package com.safety.testone.testoneofsafetynet.testcontroller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class URLRequestControllerTEST {

	@Autowired
	private MockMvc mockMvc;

	@Disabled
	@Test
	public void testListOfChildLivingInTheAdress() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert?address={adress}", "834 Binoc Ave"))
				.andExpect(status().isOk());

	}

	@Test
	public void testListOfListCoverageByStationNumber_shouldRetrun200_WhenValueExistInJson() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber={station_number}", "1"))
				.andExpect(status().isOk());

	}

	@Test
	public void testListOfListCoverageByStationNumber_shouldRetrun404_WhenValueExistInJson() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber={station_number}", "5"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation_ShouldReturn404_WhenFireStationDoesNotExist()
			throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert?firestation={firestation}", "Alex"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation_ShouldReturn200_WhenFireStationExist()
			throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert?firestation={firestation}", "1"))
				.andExpect(status().isOk());

	}

	@Test
	public void testGetListOfInhabitantAndPhoneNumberOfFireStationCloseBy_ShouldReturn404_WhenAddressDoesNotExist()
			throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/fire?address={address}", "Alex")).andExpect(status().isNotFound());

	}

	@Test
	public void testGetListOfInhabitantAndPhoneNumberOfFireStationCloseBy_ShouldReturn200_WhenAddressOfPersonDoesExist()
			throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/fire?address={address}", "1509 Culver St"))
				.andExpect(status().isOk());

	}

	@Test
	public void testGetListOfAllAddressProtectedByTheFireStation_ShouldReturn404_WhenListIsIncorrect()
			throws Exception {

		List<String> mockPurposes = new ArrayList<>();
		mockPurposes.add("a");
		mockPurposes.add("b");
		mockPurposes.add("c");

		mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations?stations={stations}", mockPurposes))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetListOfAllAddressProtectedByTheFireStation_ShouldReturn200_WhenListIsCorrect() throws Exception {

		List<String> mockPurposes = new ArrayList<>();
		mockPurposes.add("1");
		mockPurposes.add("2");
		mockPurposes.add("3");

		mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations?stations={stations}", mockPurposes))
				.andExpect(status().isOk());

	}

	@Test
	public void testgetMedicalInformationOfPeople_ShouldReturn404_WhenPersonDoesNotExist() throws Exception {

		mockMvc.perform(
				MockMvcRequestBuilders.get("/personInfo?firstName={firstName}&lastName={lastName}", "Alex", "Pierre"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testgetMedicalInformationOfPeople_ShouldReturn200_WhenPersonExistInJson() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName={?}&lastName={?}", "Jacob", "Boyd"))
				.andExpect(status().isOk());

	}

	@Test
	public void testGetAllEmailFromAllInhabitantOfCity_ShouldReturn404_WhenCityIsInccorect() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail?city={city}", "Paris"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetAllEmailFromAllInhabitantOfCity_ShouldReturn404_WhenCityExistInJson() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail?city={city}", "Culver")).andExpect(status().isOk());

	}

}
