package com.safety.savinglives.safetynetapplication.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.safety.savinglives.safetynetapplication.DTO.ChildAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.ChildInHouseAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.CommunityEmailDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireStationGeneralDTO;
import com.safety.savinglives.safetynetapplication.DTO.FloodDTO;
import com.safety.savinglives.safetynetapplication.DTO.PersonInfoDTO;
import com.safety.savinglives.safetynetapplication.DTO.PhoneAlertDTO;
import com.safety.savinglives.safetynetapplication.exception.ChildAlertException;
import com.safety.savinglives.safetynetapplication.exception.FireStationNotValid;
import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.service.URLService;

@RestController
public class URLRequestController {

	@Autowired
	URLService urlService;

	private static final Logger logger = LogManager.getLogger(URLRequestController.class);

	@GetMapping(value = "/firestation")
	public ResponseEntity getTheThing(@RequestParam(name = "stationNumber", required = false) String id) {

		FireStationGeneralDTO result = urlService.getListOfPeopleCoveredByFireStation(id);

		if (!result.getCoveredCitizens().isEmpty()) {

			logger.info("Successfully return a satisfying result call GET /firestation with ?stationNumber = " + id);
			return ResponseEntity.ok().body(result);
		} else {

			logger.error("COULD NOT return a result for GET /firestation with ?stationNumber = " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("COULD NOT return a result for /firestation with ?stationNumber = " + id);
		}

	}

	@GetMapping(value = "/childAlert")
	public ResponseEntity<ChildAlertDTO> listOfChildLivingInTheAdress(@RequestParam(name = "address", required = true) String address)
			throws Throwable {

		ChildAlertDTO result = urlService.getListOfChildBasedOnAddress(address);
		if (result.getChildInTheHouse().isEmpty()) {
			logger.error("the Call to GET /childAlert with adress " + address + " returned empty List");
			return ResponseEntity.notFound().build();
		} else {
			logger.info("the Call to GET /childAlert with adress " + address + " returned successfully");
			return ResponseEntity.ok().body(result);
		}
	}

	@GetMapping(value = "/phoneAlert")
	public ResponseEntity<Iterable<PhoneAlertDTO>> getTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation(
			@RequestParam(name = "firestation", required = true) String firestation_number) {

		List<PhoneAlertDTO> result = urlService
				.getListOfPhoneNumberOfPeopleLivingCloseToTheFireStation(firestation_number);

		if (!result.isEmpty()) {
			logger.info(
					"Successfully return a satisfying result with call GET /phoneAlert?firestation={firestation_number} with firestation_number= "
							+ firestation_number);
			return ResponseEntity.ok().body(result);
		} else {
			logger.error("COUND NOT call GET /phoneAlert?firestation={firestation_number} with firestation_number = "
					+ firestation_number);
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/fire")
	public ResponseEntity<Iterable<FireDTO>> getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(
			@RequestParam(name = "address", required = true) String address) {

		List<FireDTO> result = urlService.getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(address);

		if (!result.isEmpty()) {
			logger.info("Successfully return a satisfying result with call GET /fire?address={address} with address= " + address);
			return ResponseEntity.ok().body(result);
		} else {
			logger.error("COUND NOT call GET /fire?address={address} with address= " + address);
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping(value = "/flood/stations")
	public ResponseEntity<Iterable<FloodDTO>> getListOfAllAddressProtectedByTheFireStation(
			@RequestParam(name = "stations", required = true) List<String> IDStation) {

		List<FloodDTO> result = urlService.getListOfAllAddressProtectedByTheFireStation(IDStation);

		if (!result.isEmpty()) {
			logger.info(
					"Successfully return a satisfying result with call GET /flood/stations?stations=IDStation where IDStation = List<String> = "
							+ IDStation);
			return ResponseEntity.ok().body(result);
		} else {
			logger.error("COUND NOT call GET /flood/stations?stations=IDStation where IDStation = List<String> = "
					+ IDStation);
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = "/personInfo")
	public ResponseEntity<Iterable<PersonInfoDTO>> getMedicalInformationOfPeople(
			@RequestParam(name = "firstName", required = true) String firstName,
			@RequestParam(name = "lastName", required = true) String lastName) {

		List<PersonInfoDTO> result = urlService.getMedicalInformationOfPeople(firstName, lastName);

		if (!result.isEmpty()) {
			logger.info(
					"Successfully return a satisfying  result with call GET /personInfo?firstName={firstName}&lastName={lastName} with firstName="
							+ firstName + " , and lastName = " + lastName);
			return ResponseEntity.ok().body(result);
		} else {
			logger.error("COULD NOT call GET /personInfo?firstName={firstName}&lastName={lastName} with firstName="
					+ firstName + " , and lastName = " + lastName);
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping(value = "/communityEmail")
	public ResponseEntity<Iterable<CommunityEmailDTO>> getAllEmailFromAllInhabitantOfCity(
			@RequestParam(name = "city", required = true) String city) {

		List<CommunityEmailDTO> result = urlService.getAllEmailFromAllInhabitantOfCity(city);

		if (!result.isEmpty()) {
			logger.info("Successfully return a satisfying result with call GET /communityEmail?city={city} with city= " + city);
			return ResponseEntity.ok().body(result);
		} else {
			logger.error("COULD NOT call GET /communityEmail?city={city} with city= " + city);
			return ResponseEntity.notFound().build();
		}

	}

	public URLService getUrlService() {
		return urlService;
	}

	public void setUrlService(URLService urlService) {
		this.urlService = urlService;
	}
	
	
	
}
