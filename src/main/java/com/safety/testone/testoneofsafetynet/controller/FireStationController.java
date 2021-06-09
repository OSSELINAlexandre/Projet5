package com.safety.testone.testoneofsafetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public FireStation saveAFireStation(@RequestBody FireStation caserne) {
		return fireStationService.saveANewStation(caserne);
	}

	@DeleteMapping(value = "/firestation/{address}")
	public FireStation deleteANewStation(@PathVariable("address") String address) {

		return fireStationService.deleteANewStation(address);

	}

	@PutMapping(value = "/firestation/{address}/{newId}")
	public FireStation updateAFireStation(@PathVariable("address") String address,
			@PathVariable("newId") String newId) {

		return fireStationService.updateAStation(address, newId);
	}

	@GetMapping(value = "/firestation")
	public Iterable<FireStation> getSomeFireStation(
			@RequestParam(name = "stationNumber", required = true) String StationNumber) {
		return fireStationService.selectSomeFireStation(StationNumber);
	}
}
