package com.safety.savinglives.safetynetapplication.testcontroller;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



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
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.repository.PersonRepository;
import com.safety.savinglives.safetynetapplication.service.PersonServices;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTests {

	private MockMvc mockMvc;
	private MvcResult mvcResult;
	private Person testInstance;

	@Autowired
	private WebApplicationContext webApplicationContext;


	@Mock
	private PersonServices personservice;

	@Mock
	private PersonRepository personrepo;

	@BeforeEach
	private void beforeEach() {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		testInstance = new Person("Peter", "Duncan", "644 Gershwin Cir", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");

	}

	@Test
	public void testGet() throws Exception {

		mvcResult = mockMvc.perform(get("/person")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testDelete() throws Exception {
		mvcResult = mockMvc.perform(delete("/person/{firstName}/{thelastName}", "Jacob", "Boyd")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testDelete_return404() throws Exception {
		mvcResult = mockMvc.perform(delete("/person/{firstName}/{thelastName}", "Abraham", "Boyd")).andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testPost() throws Exception {

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/person").content(asJsonString("Bug"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(400, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testPost_return404() throws Exception {
		mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/person").content(asJsonString(testInstance))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testPut() throws Exception {

		testInstance = new Person("Jonanathan", "Marrack", "29 15th St", "Culver", "97451", "841-874-6513",
				"drk@email.com");

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/person").content(asJsonString(testInstance))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testPut_return404() throws Exception {

		mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/person").content(asJsonString("bug"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

		assertEquals(400, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testChild_Alert() throws Exception {

		mvcResult = mockMvc.perform(get("/childAlert?address={?}", "834 Binoc Ave")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	@Test
	public void testChild_Alert_return200_IfEmpty() throws Exception {

		mvcResult = mockMvc.perform(get("/childAlert?address={?}", "Light street road")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testPersonInfo() throws Exception {
		mvcResult = mockMvc.perform(get("/personInfo?firstName={?}&lastName={?}", "John", "Boyd")).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testPersonInfo_return404() throws Exception {
		mvcResult = mockMvc.perform(get("/personInfo?firstName={?}&lastName={?}", "Lolita", "Loli")).andReturn();
		assertEquals(404, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testCommunityEmail() throws Exception {
		mvcResult = mockMvc.perform(get("/communityEmail?city={?}", "Culver")).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());

	}

	@Test
	public void testCommunityEmail_return404() throws Exception {
		mvcResult = mockMvc.perform(get("/communityEmail?city={?}", "Paris")).andReturn();
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
