package com.safety.testone.testoneofsafetynet.testcontroller;

import static org.junit.jupiter.api.Assertions.*;
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
class URLRequestControllerTEST {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testListOfChildLivingInTheAdress() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert?adress={adress}", "Alex"))
		.andExpect(status().isOk());

	}
	
	@Test
	public void testGetTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert?firestation={firestation}", "Alex"))
				.andExpect(status().isOk());

	}
	
	@Test
	public void testGetListOfInhabitantAndPhoneNumberOfFireStationCloseBy() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/fire?address={address}", "Alex"))
				.andExpect(status().isOk());

	}
	
	@Test
	public void testGetListOfAllAddressProtectedByTheFireStation() throws Exception {

		List<String> mockPurposes = new ArrayList<>();
		mockPurposes.add("a");
		mockPurposes.add("b");
		mockPurposes.add("c");
		
		mockMvc.perform(MockMvcRequestBuilders.get("/flood/stations?stations={stations}", mockPurposes))
				.andExpect(status().isOk());

	}
	
	@Test
	public void testgetMedicalInformationOfPeople() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName={firstName}&lastName={lastName}", "Alex", "Pierre"))
				.andExpect(status().isOk());

	}
	
	@Test
	public void testGetAllEmailFromAllInhabitantOfCity() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail?city={email}", "Alexandre.osselin@gmail.com"))
				.andExpect(status().isOk());

	}
	

}
