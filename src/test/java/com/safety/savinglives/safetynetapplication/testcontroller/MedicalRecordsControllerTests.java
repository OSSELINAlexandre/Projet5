package com.safety.savinglives.safetynetapplication.testcontroller;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.repository.MedicalRecordRepository;
import com.safety.savinglives.safetynetapplication.service.MedicalRecordServices;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordsControllerTests {

	private MockMvc mockMvc;
	private MvcResult mvcResult;
	private MedicalRecord testInstance;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Mock
	private MedicalRecordServices medicalrecordservice;

	@Mock
	private MedicalRecordRepository medicalrecordrepo;



	@BeforeEach
	private void beforeEach() {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		List<String> medications = new ArrayList<>();
		List<String> allergies = new ArrayList<>();
		medications.add("pharmacol:5000mg");
		medications.add("terazine:10mg");
		medications.add("noznazol:250mg");

		testInstance = new MedicalRecord("Jacob", "Boyd", "03/06/1989", medications, allergies);
	}

	@AfterEach
	private void tearDown() {

	}

	@Test
	public void testDeleteMedicalRecord_shouldSend200_WhenMedicalRecordExist() throws Exception {

		mvcResult = mockMvc.perform(delete("/medicalrecords/{firstName}/{thelastName}", "John", "Boyd")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testDeleteMedicalRecord_shouldSend404_WhenMedicalRecordExist() throws Exception {

		mvcResult = mockMvc.perform(delete("/medicalrecords/{firstName}/{thelastName}", "Abraham", "Lincoln"))
				.andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testPostMedicalRecord_ShouldSend200_WhenMedicalRecordExist() throws Exception {

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecords").content(asJsonString(testInstance))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testPostMedicalRecord_ShouldSend400_WhenNotClassMedicalRecord() throws Exception {

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecords").content("lol")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(400, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testPuttMedicalRecord_ShouldSend200_WhenMedicalRecordExists() throws Exception {

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecords").content(asJsonString(testInstance))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testPuttMedicalRecord_ShouldSend404_WhenMedicalNotExist() throws Exception {

		MedicalRecord secondTest = testInstance;
		secondTest.setFirstName("Abraham");

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecords").content(asJsonString(secondTest))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testGetMedicalRecord() throws Exception {

		mvcResult = mockMvc.perform(get("/medicalrecords")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
