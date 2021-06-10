package com.safety.testone.testoneofsafetynet.testcontroller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.MedicalRecord;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetMedicalRecords() throws Exception {

		mockMvc.perform(get("/medicalrecords")).andExpect(status().isOk());
	}
	
	@Test
	public void testdeleteANewStation() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/{address}", "Alex"))
				.andExpect(status().isOk());

	}

	@Test
	public void testpostANewMedicalRecord() throws Exception {

		List<String> MockString = new ArrayList<>();
		MockString.add("QUEDALLE");
		MockString.add("RIEN");
		mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecords")
				.content(asJsonString(new MedicalRecord("Alexandre", "OSSELIN", "15/20/65", MockString, MockString)))
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
