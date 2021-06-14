package com.safety.savinglives.safetynetapplication.testcontroller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.model.Person;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordsControllerTest {

	@Autowired
	private MockMvc mockMvc;


	@Test
	public void testdeleteMedicalRecord_Shouldsend404_WhenMedcialRecordDoesNotExists() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecords/{firstName}/{thelastName}", "Alex","Osselin"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testdeleteMedcialRecord_Shouldsend200_WhenMedcialRecordExists() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecords/{firstName}/{thelastName}", "Felicia" ,"Boyd"))
				.andExpect(status().isOk());

	}

	@Test
	public void testpostANewMedicalRecord_ShouldSend404_ifIsNOTOfClassMedicalRecord() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecords").content(asJsonString("AlexOSSELIN"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}
	
	
	@Test
	public void testpostANewMedicalRecord_ShouldSend200_ifIsOfClassMedicalRecord() throws Exception {

		List<String> MockString = new ArrayList<>();
		MockString.add("QUEDALLE");
		MockString.add("RIEN");
		mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecords")
				.content(asJsonString(new MedicalRecord("Alexandre", "OSSELIN", "15/20/65", MockString, MockString)))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}


	@Test
	public void testUpdateAMedicalRecord_Shouldsend200_WhenMedicalRecordExistInJson() throws Exception {

		List<String> test = new ArrayList<>();
		test.add("YES");
		test.add("YESYES");
		mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecords")
				.content(asJsonString(new MedicalRecord("John", "Boyd", "YES", test, test)))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

	}

	@Test
	public void testUpdateAMedicalRecord_Shouldsend404_WhenMedicalRecordDoesNotExistInJson() throws Exception {

		List<String> test = new ArrayList<>();
		test.add("YES");
		test.add("YESYES");
		mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecords")
				.content(asJsonString(new MedicalRecord("LOL", "LOL", "YES", test, test)))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404));

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
