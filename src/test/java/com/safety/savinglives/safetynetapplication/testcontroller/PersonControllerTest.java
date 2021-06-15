package com.safety.savinglives.safetynetapplication.testcontroller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safety.savinglives.safetynetapplication.DTO.childAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.childInHouseAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.communityEmailDTO;
import com.safety.savinglives.safetynetapplication.DTO.personInfoDTO;
import com.safety.savinglives.safetynetapplication.controller.PersonController;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.service.URLService;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private URLService urlService;

	@Autowired
	private PersonController personcontroller;

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

		mockMvc.perform(MockMvcRequestBuilders.put("/person")
				.content(
						asJsonString(new Person("John", "Boyd", "YES", "YES", "YES", "841-874-6544", "jaboyd@YES.com")))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

	}

	@Test
	public void testUpdateAPerson_Shouldsend404_WhenContactDoesNotExistInJson() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.put("/person")
				.content(asJsonString(new Person("LOL", "LOL", "YES", "YES", "YES", "841-874-6544", "jaboyd@YES.com")))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404));

	}

	@Test
	public void testListOfChildLivingInTheAdress_ShouldReturn404_WhenAdressDoesNotContainChildren() throws Exception {

		childAlertDTO testItem = new childAlertDTO(new ArrayList<childInHouseAlertDTO>(), new ArrayList<Person>());
		when(urlService.getListOfChildBasedOnAddress("32 rue du chemin")).thenReturn(testItem);
		personcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert?address={adress}", "32 rue du chemin"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testListOfChildLivingInTheAdress_ShouldReturn200_WhenAdressDoContainChildren() throws Exception {

		childInHouseAlertDTO childHouse = new childInHouseAlertDTO("Sophie", "Lecomte", "6");
		ArrayList<childInHouseAlertDTO> childenInHouse = new ArrayList<>();
		childenInHouse.add(childHouse);
		ArrayList<Person> adultInHouse = new ArrayList<>();

		childAlertDTO testItem = new childAlertDTO(childenInHouse, adultInHouse);

		when(urlService.getListOfChildBasedOnAddress("32 rue du chemin")).thenReturn(testItem);
		personcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/childAlert?address={adress}", "32 rue du chemin"))
				.andExpect(status().isOk());

	}

	@Test
	public void testgetMedicalInformationOfPeople_ShouldReturn404_WhenPersonDoesNotExist() throws Exception {

		List<personInfoDTO> testItem = new ArrayList<>();

		when(urlService.getMedicalInformationOfPeople("Alex", "Osselin")).thenReturn(testItem);
		personcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName={?}&lastName={?}", "Alex", "Osselin"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testgetMedicalInformationOfPeople_ShouldReturn200_WhenPersonExistInJson() throws Exception {

		List<String> meds = new ArrayList<>();
		meds.add("Dolipranne : 200mg");
		meds.add("Pollen");

		personInfoDTO itema = new personInfoDTO("Osselin", "Alex", "32 rue du chemin", "25", "codeurjava@gmail.com",
				meds);
		List<personInfoDTO> testItem = new ArrayList<>();
		testItem.add(itema);

		when(urlService.getMedicalInformationOfPeople("Alex", "Osselin")).thenReturn(testItem);
		personcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName={Jacob}&lastName={Boyd}", "Alex", "Osselin"))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetAllEmailFromAllInhabitantOfCity_ShouldReturn404_WhenCityIsInccorect() throws Exception {

		List<communityEmailDTO> testItem = new ArrayList<>();

		when(urlService.getAllEmailFromAllInhabitantOfCity("Paris")).thenReturn(testItem);
		personcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail?city={city}", "Paris"))
				.andExpect(status().isNotFound());

	}

	@Test
	public void testGetAllEmailFromAllInhabitantOfCity_ShouldReturn200_WhenCityExistInJson() throws Exception {

		communityEmailDTO itemA = new communityEmailDTO("codeurjava@gmail.com");
		communityEmailDTO itemB = new communityEmailDTO("bernard.arnaud@lvmh.com");
		communityEmailDTO itemC = new communityEmailDTO("sophie.Lecomte@gmail.com");
		communityEmailDTO itemD = new communityEmailDTO("cain.cain@gmail.com");

		List<communityEmailDTO> testItem = new ArrayList<>();
		testItem.add(itemA);
		testItem.add(itemB);
		testItem.add(itemC);
		testItem.add(itemD);

		when(urlService.getAllEmailFromAllInhabitantOfCity("Paris")).thenReturn(testItem);
		personcontroller.setUrlService(urlService);

		mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail?city={city}", "Paris")).andExpect(status().isOk());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
