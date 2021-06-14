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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.model.Person;

@SpringBootTest
@AutoConfigureMockMvc
class FireStationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testdeleteFireStation_Shouldsend404_WhenFireStationDoesNotExists() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/{address}", "Alex"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testdeleteFireStation_Shouldsend200_WhenFireStationExists() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/{address}", "908 73rd St"))
				.andExpect(status().isOk());

	}

	@Test
	public void testpostANewFireStation_ShouldSend404_ifIsNOTOfClassFireStation() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/firestation").content(asJsonString("AlexOSSELIN"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}

	@Test
	public void testpostANewFireStation_ShouldSend200_ifIsOfClassFireStation() throws Exception {


		mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
				.content(asJsonString(new FireStation("Christ Church Avenue, Oxford", "2")))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testUpdateAFireStation_Shouldsend200_WhenFireStationExistInJson() throws Exception {

		
		mockMvc.perform(MockMvcRequestBuilders.put("/firestation/{address}/{newId}", "892 Downing Ct" , "7"))
		.andExpect(status().isOk());

	}

	@Test
	public void testUpdateAFireStation_Shouldsend404_WhenFireStationDoesNotExistInJson() throws Exception {


		mockMvc.perform(MockMvcRequestBuilders.put("/firestation/{address}/{newId}", "Christ Church Avenue, Oxford", "2"))
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
