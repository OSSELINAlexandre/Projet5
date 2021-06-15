package com.safety.savinglives.safetynetapplication.testcontroller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class URLRequestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testListOfChildLivingInTheAdress_ShouldReturn404_WhenAdressDoesNotContainChildren() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert?address={adress}", "644 Gershwin Cir"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testListOfChildLivingInTheAdress_ShouldReturn200_WhenAdressDoesNotContainChildren() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert?address={adress}", "1509 Culver St"))
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

		mockMvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName={Jacob}&lastName={Boyd}", "Alex", "O"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testgetMedicalInformationOfPeople_ShouldReturn200_WhenPersonExistInJson() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName={Jacob}&lastName={Boyd}", "Jacob", "Boyd"))
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
