package com.safety.savinglives.safetynetapplication.testcontroller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.savinglives.safetynetapplication.DTO.fireDTO;
import com.safety.savinglives.safetynetapplication.DTO.firePersonDTO;
import com.safety.savinglives.safetynetapplication.DTO.fireStationDTO;
import com.safety.savinglives.safetynetapplication.DTO.fireStationGeneralDTO;
import com.safety.savinglives.safetynetapplication.DTO.floodDTO;
import com.safety.savinglives.safetynetapplication.DTO.phoneAlertDTO;
import com.safety.savinglives.safetynetapplication.controller.FireStationController;
import com.safety.savinglives.safetynetapplication.controller.MedicalRecordsController;
import com.safety.savinglives.safetynetapplication.controller.PersonController;
import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.repository.FireStationRepository;
import com.safety.savinglives.safetynetapplication.repository.MedicalRecordRepository;
import com.safety.savinglives.safetynetapplication.service.FireStationService;
import com.safety.savinglives.safetynetapplication.service.MedicalRecordService;
import com.safety.savinglives.safetynetapplication.service.URLService;

@SpringBootTest
@AutoConfigureMockMvc
class FireStationControllerTest {

	private MockMvc mockMvc;
	private MvcResult mvcResult;
	private FireStation testInstance;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private FireStationController firestationcontroller;

	@Mock
	private FireStationService firestationservice;

	@Mock
	private FireStationRepository firestationrecordrepo;

	@Mock
	private URLService urlService;

	@BeforeEach
	private void beforeEach() {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		testInstance = new FireStation("644 Gershwin Cir", "1");

	}

	@Test
	public void testdeleteFireStation_Shouldsend404_WhenFireStationDoesNotExists() throws Exception {

		mvcResult = mockMvc.perform(delete("/firestation/{address}", "Light Street")).andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testdeleteFireStation_Shouldsend200_WhenFireStationExists() throws Exception {

		mvcResult = mockMvc.perform(delete("/firestation/{address}", "748 Townings Dr")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testpostANewFireStation_ShouldSend400_ifIsNotOfClassFireStation() throws Exception {

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/firestation").content(asJsonString("Bug"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(400, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testpostANewFireStation_ShouldSend200_ifIsOfClassFireStation() throws Exception {

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/firestation").content(asJsonString(testInstance))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testPutAFireStation_Shouldsend200_WhenFireStationExistInJson() throws Exception {

		mvcResult = mockMvc.perform(put("/firestation/{address}/{newId}", "1509 Culver St", "7")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testPutAFireStation_Shouldsend404_WhenFireStationDoesNotExistInJson() throws Exception {

		mvcResult = mockMvc.perform(put("/firestation/{address}/{newId}", "Light Road Street", "7")).andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testGetFireStation_ShouldReturn200_WhenStationExists() throws Exception {

		mvcResult = mockMvc.perform(get("/firestation?stationNumber={?}", "1")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testGetFireStation_ShouldReturn404_WhenStationExists() throws Exception {

		mvcResult = mockMvc.perform(get("/firestation?stationNumber={?}", "77")).andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testListCoverageByStationNumber_shouldRetrun404_WhenValueDoesNotExistInJson() throws Exception {

		mvcResult = mockMvc.perform(put("/firestation/{address}/{newId}", "Light Road Street", "7")).andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testPhoneAlert() throws Exception {

		mvcResult = mockMvc.perform(get("/phoneAlert?firestation={?}", "1")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testPhoneAlert_return404() throws Exception {

		mvcResult = mockMvc.perform(get("/phoneAlert?firestation={?}", "77")).andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testFire() throws Exception {

		mvcResult = mockMvc.perform(get("/fire?address={?}", "834 Binoc Ave")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testFire_return404() throws Exception {

		mvcResult = mockMvc.perform(get("/fire?address={?}", "LIGHT ROAD STREET")).andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testFlood() throws Exception {

		mvcResult = mockMvc.perform(get("/flood/stations?stations={?}", "1,2")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testFlood_return404() throws Exception {

		mvcResult = mockMvc.perform(get("/flood/stations?stations={?}", "99,100")).andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
