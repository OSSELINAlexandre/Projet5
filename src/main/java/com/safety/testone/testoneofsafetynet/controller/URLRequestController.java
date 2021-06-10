package com.safety.testone.testoneofsafetynet.controller;

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

import com.safety.testone.testoneofsafetynet.DTO.ChildAlertDTO;
import com.safety.testone.testoneofsafetynet.DTO.ChildInHouseAlertDTO;
import com.safety.testone.testoneofsafetynet.DTO.CommunityEmailDTO;
import com.safety.testone.testoneofsafetynet.DTO.FireDTO;
import com.safety.testone.testoneofsafetynet.DTO.FireStationGeneralDTO;
import com.safety.testone.testoneofsafetynet.DTO.FloodDTO;
import com.safety.testone.testoneofsafetynet.DTO.PersonInfoDTO;
import com.safety.testone.testoneofsafetynet.DTO.PhoneAlertDTO;
import com.safety.testone.testoneofsafetynet.controller.exception.FireStationNotValid;
import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.MedicalRecord;
import com.safety.testone.testoneofsafetynet.service.URLService;

@RestController
public class URLRequestController {

	@Autowired
	URLService urlService;

	private static final Logger logger = LogManager.getLogger(URLRequestController.class);

	@GetMapping(value = "/firestation")
	public ResponseEntity getTheThing(
			@RequestParam(name = "stationNumber", required = false) String id) {

		FireStationGeneralDTO result = urlService.getListOfPeopleCoveredByFireStation(id);

		if (!result.getCoveredCitizens().isEmpty()) {

			logger.info("Successfully return a result for /firestation with ?stationNumber = " + id);
			return ResponseEntity.ok().body(result);
		} else {

			logger.error("COULD NOT return a result for /firestation with ?stationNumber = " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("COULD NOT return a result for /firestation with ?stationNumber = " + id);
		}

	}

	@GetMapping(value = "/childAlert")
	public ChildAlertDTO listOfChildLivingInTheAdress(@RequestParam(name = "address", required = true) String address) {

		ChildAlertDTO result = urlService.getListOfChildBasedOnAddress(address);
		if (result.getChildInTheHouse().isEmpty()) {
			logger.error("the Call to ChildAlert with adress " + address + "returned empty List");
			return result;
		} else {
			logger.info("the Call to ChildAlert with adress " + address + "returned successfully");
			return result;
		}
	}

	@GetMapping(value = "/phoneAlert")
	public Iterable<PhoneAlertDTO> getTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation(
			@RequestParam(name = "firestation", required = true) String firestation_number) {

		List<PhoneAlertDTO> result = urlService
				.getListOfPhoneNumberOfPeopleLivingCloseToTheFireStation(firestation_number);

		if (!result.isEmpty()) {
			logger.info(
					"Sucessuffly called method getTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation in URLRequestController");
			return result;
		} else {
			logger.error(
					"Couldn't return a result for method getTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation in URLRequestController");
			return result;
		}
	}

	@GetMapping(value = "/fire")
	public Iterable<FireDTO> getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(
			@RequestParam(name = "address", required = true) String address) {

		List<FireDTO> result = urlService.getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(address);

		if (!result.isEmpty()) {
			logger.info(
					"Sucessuffly called method getListOfInhabitantAndPhoneNumberOfFireStationCloseBy in URLRequestController");
			return result;
		} else {
			logger.error(
					"Couldn't return a result for method getListOfInhabitantAndPhoneNumberOfFireStationCloseBy in URLRequestController");
			return result;
		}

	}

	@GetMapping(value = "/flood/stations")
	public Iterable<FloodDTO> getListOfAllAddressProtectedByTheFireStation(
			@RequestParam(name = "stations", required = true) List<String> IDStation) {

		List<FloodDTO> result = urlService.getListOfAllAddressProtectedByTheFireStation(IDStation);

		if (!result.isEmpty()) {
			logger.info(
					"Sucessuffly called method getListOfAllAddressProtectedByTheFireStation in URLRequestController");
			return result;
		} else {
			logger.error(
					"Couldn't return a result for method getListOfAllAddressProtectedByTheFireStation in URLRequestController");
			return result;
		}
	}

	@GetMapping(value = "/personInfo")
	public Iterable<PersonInfoDTO> getMedicalInformationOfPeople(
			@RequestParam(name = "firstName", required = true) String firstName,
			@RequestParam(name = "lastName", required = true) String lastName) {

		List<PersonInfoDTO> result = urlService.getMedicalInformationOfPeople(firstName, lastName);

		if (!result.isEmpty()) {
			logger.info("Sucessuffly called method getMedicalInformationOfPeople in URLRequestController");
			return result;
		} else {
			logger.error("Couldn't return a result for method getMedicalInformationOfPeople in URLRequestController");
			return result;
		}

	}

	@GetMapping(value = "/communityEmail")
	public Iterable<CommunityEmailDTO> getAllEmailFromAllInhabitantOfCity(
			@RequestParam(name = "city", required = true) String city) {

		List<CommunityEmailDTO> result = urlService.getAllEmailFromAllInhabitantOfCity(city);

		if (!result.isEmpty()) {
			logger.info("Sucessuffly called method getAllEmailFromAllInhabitantOfCity in URLRequestController");
			return result;
		} else {
			logger.error(
					"Couldn't return a result for method getAllEmailFromAllInhabitantOfCity in URLRequestController");
			return result;
		}

	}
}
