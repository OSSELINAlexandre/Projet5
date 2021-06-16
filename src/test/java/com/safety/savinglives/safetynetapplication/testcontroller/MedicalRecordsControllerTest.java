package com.safety.savinglives.safetynetapplication.testcontroller;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.savinglives.safetynetapplication.controller.FireStationController;
import com.safety.savinglives.safetynetapplication.controller.MedicalRecordsController;
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
class MedicalRecordsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private URLService urlService;

	@Mock
	private MedicalRecordService medicalrecordservice;

	@Autowired
	private MedicalRecordsController medicalrecordcontroller;

	@Mock
	private MedicalRecordRepository medicalrecordrepo;

	@Test
	public void testdeleteMedicalRecord_Shouldsend404_WhenMedcialRecordDoesNotExists() throws Exception {

		when(medicalrecordservice.deleteAMedicalFile("Alex", "Osselin")).thenReturn(false);

		medicalrecordcontroller.setMedicalRecordService(medicalrecordservice);

		mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecords/{firstName}/{thelastName}", "Alex", "Osselin"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testdeleteMedcialRecord_Shouldsend200_WhenMedcialRecordExists() throws Exception {

		when(medicalrecordservice.deleteAMedicalFile("Alex", "Osselin")).thenReturn(true);
		medicalrecordcontroller.setMedicalRecordService(medicalrecordservice);

		mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecords/{firstName}/{thelastName}", "Alex", "Osselin"))
				.andExpect(status().isOk());

	}

	@Test
	public void testpostANewMedicalRecord_ShouldSend400_ifIsNOTOfClassMedicalRecord() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecords").content(asJsonString("nogoodform"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}

	@Test
	public void testpostANewMedicalRecord_ShouldSend200_ifIsOfClassMedicalRecord() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecords").content(asJsonString(new MedicalRecord()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testUpdateAMedicalRecord_Shouldsend200_WhenMedicalRecordExistInJson() throws Exception {


		
		List<String> testList = new ArrayList<>();
		testList.add("LOL");
		testList.add("MDR");
		MedicalRecord testItem = new MedicalRecord("Alexandre", "OSSELIN", "11/06/1995", testList, testList);
		when(medicalrecordservice.updateAMedicalFile(testItem)).thenReturn(testItem);
		medicalrecordcontroller.setMedicalRecordService(medicalrecordservice);
				
		
		mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecords").content(asJsonString(testItem))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
	}

	@Test
	public void testUpdateAMedicalRecord_Shouldsend404_WhenMedicalRecordDoesNotExistInJson() throws Exception {

		List<String> test = new ArrayList<>();
		test.add("YES");
		test.add("YESYES");
		MedicalRecord testItem = new MedicalRecord("Alexandre", "OSSELIN", "06/11/1995", test, test);
		when(medicalrecordservice.updateAMedicalFile(testItem)).thenReturn(null);
		medicalrecordcontroller.setMedicalRecordService(medicalrecordservice);

		mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecords").content(asJsonString(testItem))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
