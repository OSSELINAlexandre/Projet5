package com.safety.savinglives.safetynetapplication.testcontroller;

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
import com.safety.savinglives.safetynetapplication.model.Person;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetPerson() throws Exception {

		mockMvc.perform(get("/person")).andExpect(status().isOk());

	}

	@Test
	public void testDeleteAPerson_ShouldSend404_WhenWrongIdentification() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/person/{firstName}/{thelastName}", "Alex", "Lol"))
				.andExpect(status().is(404));

	}

	@Test
	public void testDeleteAPerson_ShouldSend200_WhenGoodIdentification() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/person/{firstName}/{thelastName}", "Tessa", "Carman"))
				.andExpect(status().is(200));

	}

	@Test
	public void testSaveAPerson_Shouldsend200_WhenClassisPerson() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/person")
				.content(asJsonString(new Person("Alex", "Osselin", "rue sacrécoeur", "Vanves", "98652", "888-88-88-77",
						"holding@gmail.fr")))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	public void testSaveAPerson_Shouldsend400_WhenClassisNotAPerson() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/person").content(asJsonString("AlexOSSELIN"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));

	}

	
	@Test
	public void testUpdateAPerson_Shouldsend200_WhenContactExistInJson() throws Exception {
		
		
		mockMvc.perform(MockMvcRequestBuilders.put("/person").content(asJsonString(new Person("John" , "Boyd" , "YES" ,"YES", "YES", "841-874-6544", "jaboyd@YES.com" ) ))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

		
	}
	
	@Test
	public void testUpdateAPerson_Shouldsend404_WhenContactDoesNotExistInJson() throws Exception {
		
		
		mockMvc.perform(MockMvcRequestBuilders.put("/person").content(asJsonString(new Person("LOL" , "LOL" , "YES" ,"YES", "YES", "841-874-6544", "jaboyd@YES.com" ) ))
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
