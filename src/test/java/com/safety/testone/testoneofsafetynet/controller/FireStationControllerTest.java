package com.safety.testone.testoneofsafetynet.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.Person;

@SpringBootTest
@AutoConfigureMockMvc
class FireStationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	
	@Test
	public void testdeleteANewStation() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/{address}", "Alex"))
				.andExpect(status().isOk());

	}

	@Test
	public void testSaveAPerson() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
				.content(asJsonString(new FireStation("rue du sacre coeur", "526")))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
//TODO changer les codes de retours des applications		
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Test
	public void testUpdateAFireStation() throws Exception {

		mockMvc.perform( MockMvcRequestBuilders
			      .put("/firestation/{address}/{newId}", "32 RUE DU MOULIN" , "2")
			      .content(asJsonString(new FireStation( "firstName2","2")))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());

	}

}
