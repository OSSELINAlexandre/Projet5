package com.safety.savinglives.safetynetapplication.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safety.savinglives.safetynetapplication.DTO.fireDTO;
import com.safety.savinglives.safetynetapplication.DTO.firePersonDTO;
import com.safety.savinglives.safetynetapplication.DTO.fireStationGeneralDTO;
import com.safety.savinglives.safetynetapplication.DTO.floodDTO;
import com.safety.savinglives.safetynetapplication.DTO.phoneAlertDTO;
import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.service.FireStationService;
import com.safety.savinglives.safetynetapplication.service.URLService;

@RestController
public class FireStationController {

	private static final Logger logger = LogManager.getLogger(FireStationController.class);

	@Autowired
	FireStationService fireStationService;

	@Autowired
	URLService urlService;

	@PostMapping(value = "/firestation")
	public ResponseEntity<Void> saveAFireStation(@RequestBody FireStation caserne) {

		Boolean result = fireStationService.saveANewStation(caserne);

		if (result) {
			logger.info("Successfully return a satisfying  result for POST /firestation ");
			return ResponseEntity.ok().build();
		} else {
			logger.error("COULD NOT return a satisfying result for POST /firestation ");
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(value = "/firestation/{address}")
	public ResponseEntity<Void> deleteANewStation(@PathVariable("address") String address) {

		Boolean result = fireStationService.deleteANewStation(address);

		if (result) {
			logger.info("Successfully return a satisfying  result for DELETE /firestation/{address} with address =  "
					+ address);
			return ResponseEntity.ok().build();
		} else {
			logger.error("COULD NOT return a satisfying result for DELETE /firestation/{address} with address =  "
					+ address);
			return ResponseEntity.notFound().build();
		}

	}

	@PutMapping(value = "/firestation/{address}/{newId}")
	public ResponseEntity<Void> updateAFireStation(@PathVariable("address") String address,
			@PathVariable("newId") String newId) {

		FireStation result = fireStationService.updateAStation(address, newId);
		if (result != null) {
			logger.info(
					"Successfully return a satisfying  result for PUT /firestation/{address}/{newId} with address =  "
							+ address + " and ID = " + newId);
			return ResponseEntity.ok().build();
		} else {
			logger.error("COULD NOT return a satisfying result for PUT /firestation/{address}/{newId} with address =  "
					+ address + " and ID = " + newId);
			return ResponseEntity.notFound().build();

		}

	}

	@GetMapping(value = "/firestation")
	public ResponseEntity getTheThing(@RequestParam(name = "stationNumber", required = false) String id) {

		fireStationGeneralDTO result = urlService.getListOfPeopleCoveredByFireStation(id);

		if (!result.getCoveredCitizens().isEmpty()) {

			logger.info("Successfully return a satisfying result call GET /firestation with ?stationNumber = " + id);
			return ResponseEntity.ok().body(result);
		} else {

			logger.error("COULD NOT return a result for GET /firestation with ?stationNumber = " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("COULD NOT return a result for /firestation with ?stationNumber = " + id);
		}

	}

	@GetMapping(value = "/phoneAlert")
	public ResponseEntity<Iterable<phoneAlertDTO>> getTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation(
			@RequestParam(name = "firestation", required = true) String firestation_number) {

		List<phoneAlertDTO> result = urlService
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
	public ResponseEntity<fireDTO> getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(
			@RequestParam(name = "address", required = true) String address) {

		fireDTO result = urlService.getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(address);

		if (!result.getCitizenLivingAtTheAddress().isEmpty()) {
			logger.info("Successfully return a satisfying result with call GET /fire?address={address} with address= "
					+ address);
			return ResponseEntity.ok().body(result);
		} else {
			logger.error("COUND NOT call GET /fire?address={address} with address= " + address);
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping(value = "/flood/stations")
	public ResponseEntity<Iterable<floodDTO>> getListOfAllAddressProtectedByTheFireStation(
			@RequestParam(name = "stations", required = true) List<String> IDStation) {

		List<floodDTO> result = urlService.getListOfAllAddressProtectedByTheFireStation(IDStation);

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



}
