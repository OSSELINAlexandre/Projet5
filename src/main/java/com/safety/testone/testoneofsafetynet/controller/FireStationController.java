package com.safety.testone.testoneofsafetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.Person;
import com.safety.testone.testoneofsafetynet.service.FireStationService;

@RestController
public class FireStationController {

	@Autowired
	FireStationService fireStationService;

	@GetMapping(value = "/firestation")
	public Iterable<FireStation> getAllFireStation() {

		return fireStationService.getAllFireStations();
	}

	@PostMapping(value = "/firestation")
	public FireStation saveAPerson(@RequestBody FireStation caserne) {
		return fireStationService.saveANewStation(caserne);
	}

	@DeleteMapping(value = "/firestation/{address}")
	public FireStation createAFireStation(@PathVariable("address") String address) {

		return fireStationService.deleteANewStation(address);

	}

	@PutMapping(value = "/firestation/{address}/{newId}")
	public FireStation updateAFireStation(@PathVariable("address") String address,
			@PathVariable("newId") String newId) {

		return fireStationService.updateAStation(address, newId);
	}
}
