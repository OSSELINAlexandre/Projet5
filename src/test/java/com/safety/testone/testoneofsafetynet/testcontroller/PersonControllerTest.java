package com.safety.testone.testoneofsafetynet.testcontroller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.testone.testoneofsafetynet.model.Person;

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
	public void testDeleteAPerson() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/person/{firstName}/{thelastName}", "Alex", "Lol"))
				.andExpect(status().isOk());

	}

	@Test
	public void testSaveAPerson() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/person")
				.content(asJsonString(new Person("Alex", "Osselin", "rue sacrécoeur", "Vanves", "98652", "888-88-88-77",
						"holding@gmail.fr")))
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

}
