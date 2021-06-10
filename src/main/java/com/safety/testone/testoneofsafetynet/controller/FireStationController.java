package com.safety.testone.testoneofsafetynet.controller;

import java.util.List;

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

import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.Person;
import com.safety.testone.testoneofsafetynet.service.FireStationService;

@RestController
public class FireStationController {

	@Autowired
	FireStationService fireStationService;

	@PostMapping(value = "/firestation")
	public ResponseEntity<Void> saveAFireStation(@RequestBody FireStation caserne) {

		Boolean result = fireStationService.saveANewStation(caserne);

		if (result) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(value = "/firestation/{address}")
	public ResponseEntity<Void> deleteANewStation(@PathVariable("address") String address) {

		Boolean result = fireStationService.deleteANewStation(address);

		if (result) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PutMapping(value = "/firestation/{address}/{newId}")
	public ResponseEntity<Void> updateAFireStation(@PathVariable("address") String address,
			@PathVariable("newId") String newId) {

		FireStation result = fireStationService.updateAStation(address, newId);
		if (result == null) {

			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.accepted().build();

		}

	}

}
