package com.safety.testone.testoneofsafetynet.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	
	private static final Logger logger = LogManager.getLogger(URLRequestController.class);
	

	@GetMapping(value = "/childAlert")
	public Iterable<childAlertDTO> listOfChildLivingInTheAdress(
			@RequestParam(name = "adress", required = true) String adress) {
		
		logger.error("Issue with this one");
		return urlService.getListOfChildBasedOnAddress(adress);
	}

	@GetMapping(value = "/phoneAlert")
	public Iterable<phoneAlertDTO> getTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation(
			@RequestParam(name = "firestation", required = true) String firestation_number) {
		
		List<phoneAlertDTO> result = urlService.getListOfPhoneNumberOfPeopleLivingCloseToTheFireStation(firestation_number);
		
		if(!result.isEmpty()) {
			logger.info("Sucessuffly called method getTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation in URLRequestController");
			return result;
		}else {
			logger.error("Couldn't return a result for method getTheListOfPhoneNumberOfPeopleLivingCloseToTheFireStation in URLRequestController");
			return result;
		}
	}

	@GetMapping(value = "/fire")
	public Iterable<fireDTO> getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(
			@RequestParam(name = "address", required = true) String address) {
		
		List<fireDTO> result = urlService.getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(address);
		
		if(!result.isEmpty()) {
			logger.info("Sucessuffly called method getListOfInhabitantAndPhoneNumberOfFireStationCloseBy in URLRequestController");
			return result;
		}else {
			logger.error("Couldn't return a result for method getListOfInhabitantAndPhoneNumberOfFireStationCloseBy in URLRequestController");
			return result;
		}
		
	}

	@GetMapping(value = "/flood/stations")
	public Iterable<floodDTO> getListOfAllAddressProtectedByTheFireStation(
			@RequestParam(name = "stations", required = true) List<String> IDStation) {
		
		List<floodDTO> result = urlService.getListOfAllAddressProtectedByTheFireStation(IDStation);
		
		if(!result.isEmpty()) {
			logger.info("Sucessuffly called method getListOfAllAddressProtectedByTheFireStation in URLRequestController");
			return result;
		}else {
			logger.error("Couldn't return a result for method getListOfAllAddressProtectedByTheFireStation in URLRequestController");
			return result;
		}
	}

	@GetMapping(value = "/personInfo")
	public Iterable<personInfoDTO> getMedicalInformationOfPeople(
			@RequestParam(name = "firstName", required = true) String firstName,
			@RequestParam(name = "lastName", required = true) String lastName) {
		
		List<personInfoDTO> result = urlService.getMedicalInformationOfPeople(firstName, lastName);
		
		if(!result.isEmpty()) {
			logger.info("Sucessuffly called method getMedicalInformationOfPeople in URLRequestController");
			return result;
		}else {
			logger.error("Couldn't return a result for method getMedicalInformationOfPeople in URLRequestController");
			return result;
		}

	}

	@GetMapping(value = "/communityEmail")
	public Iterable<communityEmailDTO> getAllEmailFromAllInhabitantOfCity(
			@RequestParam(name = "city", required = true) String city) {
		
		List<communityEmailDTO> result = urlService.getAllEmailFromAllInhabitantOfCity(city);
		
		if(!result.isEmpty()) {
			logger.info("Sucessuffly called method getAllEmailFromAllInhabitantOfCity in URLRequestController");
			return result;
		}else {
			logger.error("Couldn't return a result for method getAllEmailFromAllInhabitantOfCity in URLRequestController");
			return result;
		}
	
	}
}
