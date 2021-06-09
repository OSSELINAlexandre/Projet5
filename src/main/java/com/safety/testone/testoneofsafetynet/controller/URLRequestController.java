package com.safety.testone.testoneofsafetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safety.testone.testoneofsafetynet.DTO.childAlertDTO;
import com.safety.testone.testoneofsafetynet.DTO.communityEmailDTO;
import com.safety.testone.testoneofsafetynet.DTO.fireDTO;
import com.safety.testone.testoneofsafetynet.DTO.floodDTO;
import com.safety.testone.testoneofsafetynet.DTO.personInfoDTO;
import com.safety.testone.testoneofsafetynet.DTO.phoneAlertDTO;
import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.service.URLService;

@RestController
public class URLRequestController {

	@Autowired
	URLService urlService;

	@GetMapping(value = "/childAlert")
	public Iterable<childAlertDTO> listOfChildLivingInTheAdress(
			@RequestParam(name = "adress", required = true) String adress) {
		return urlService.getListOfChildBasedOnAddress(adress);
	}

	@GetMapping(value = "/phoneAlert")
	public Iterable<phoneAlertDTO> getTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation(
			@RequestParam(name = "firestation", required = true) String firestation_number) {
		return urlService.getListOfPhoneNumberOfPeopleLivingCloseToTheFireStation(firestation_number);
	}

	@GetMapping(value = "/fire")
	public Iterable<fireDTO> getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(
			@RequestParam(name = "address", required = true) String address) {
		return urlService.getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(address);
	}

	@GetMapping(value = "/flood/stations")
	public Iterable<floodDTO> getListOfAllAddressProtectedByTheFireStation(
			@RequestParam(name = "stations", required = true) List<String> IDStation) {
		return urlService.getListOfAllAddressProtectedByTheFireStation(IDStation);
	}

	@GetMapping(value = "/personInfo")
	public Iterable<personInfoDTO> getMedicalInformationOfPeople(
			@RequestParam(name = "firstName", required = true) String firstName,
			@RequestParam(name = "lastName", required = true) String lastName) {
		return urlService.getMedicalInformationOfPeople(firstName, lastName);
	}

	@GetMapping(value = "/communityEmail")
	public Iterable<communityEmailDTO> getAllEmailFromAllInhabitantOfCity(
			@RequestParam(name = "city", required = true) String city) {
		return urlService.getAllEmailFromAllInhabitantOfCity(city);
	}
}
