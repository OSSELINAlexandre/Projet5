package com.safety.savinglives.safetynetapplication.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.service.FireStationService;

@RestController
public class FireStationController {

	private static final Logger logger = LogManager.getLogger(FireStationController.class);

	@Autowired
	FireStationService fireStationService;

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

}
