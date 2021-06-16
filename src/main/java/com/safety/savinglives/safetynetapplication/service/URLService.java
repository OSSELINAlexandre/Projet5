package com.safety.savinglives.safetynetapplication.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.savinglives.safetynetapplication.DTO.childAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.childInHouseAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.communityEmailDTO;
import com.safety.savinglives.safetynetapplication.DTO.fireDTO;
import com.safety.savinglives.safetynetapplication.DTO.firePersonDTO;
import com.safety.savinglives.safetynetapplication.DTO.fireStationDTO;
import com.safety.savinglives.safetynetapplication.DTO.fireStationGeneralDTO;
import com.safety.savinglives.safetynetapplication.DTO.floodDTO;
import com.safety.savinglives.safetynetapplication.DTO.personInfoDTO;
import com.safety.savinglives.safetynetapplication.DTO.phoneAlertDTO;
import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.repository.FireStationRepository;
import com.safety.savinglives.safetynetapplication.repository.MedicalRecordRepository;
import com.safety.savinglives.safetynetapplication.repository.PersonRepository;

@Service
public class URLService {

	private static final Logger logger = LogManager.getLogger(URLService.class);

	@Autowired
	PersonRepository personRepository;

	@Autowired
	FireStationRepository fireStationRepository;

	@Autowired
	MedicalRecordRepository medicalRecordRepository;

	public URLService() {
		super();
	}

	public fireStationGeneralDTO getListOfPeopleCoveredByFireStation(String id) {

		ArrayList<String> adressCoveredByStation = new ArrayList<>();

		for (FireStation s : fireStationRepository.getAllData()) {

			if (s.getStation().equals(id)) {

				adressCoveredByStation.add(s.getAddress());
				logger.debug(s.getAddress() + " added to the adressCoveredByStation for id " + id);
			}

		}

		ArrayList<fireStationDTO> citizenLivingClose = new ArrayList<>();

		for (String s : adressCoveredByStation) {

			for (Person p : personRepository.getAllData()) {

				if (p.getAddress().equals(s)) {

					logger.debug(p.getFirstName() + ", " + p.getLastName()
							+ " supposed to live in the area of fireStation number  " + id);

					fireStationDTO newItem = new fireStationDTO(p.getFirstName(), p.getLastName(), p.getAddress(),
							p.getPhone());
					citizenLivingClose.add(newItem);

				}

			}
		}

		fireStationGeneralDTO result = new fireStationGeneralDTO(citizenLivingClose, 0, 0);

		for (fireStationDTO p : citizenLivingClose) {

			for (MedicalRecord medRec : medicalRecordRepository.getAllData()) {

				if (medRec.getFirstName().equals(p.getFirstName()) && medRec.getLastName().equals(p.getLastName())) {

					if (getTheAge(medRec.getBirthdate()) <= 18) {
						logger.debug("This is the age of the CHILD " + getTheAge(medRec.getBirthdate()));
						int Result = result.getChildCount() + 1;
						result.setChildCount(Result);
					} else {
						logger.debug("This is the age of the ADULT " + getTheAge(medRec.getBirthdate()));
						int Result = result.getAdultCount() + 1;
						result.setAdultCount(Result);

					}

				}
			}

		}

		return result;

	}

	public childAlertDTO getListOfChildBasedOnAddress(String adress) {
		List<Person> livingAtThisAdress = new ArrayList<Person>();
		List<childInHouseAlertDTO> livingAtThisAdressUnder18 = new ArrayList<childInHouseAlertDTO>();
		List<Person> livingAtThisAdressAbove18 = new ArrayList<Person>();
		childInHouseAlertDTO newChild = new childInHouseAlertDTO();
		for (Person p : personRepository.getAllData()) {
			if (p.getAddress().equals(adress)) {
				livingAtThisAdress.add(p);
			}
		}

		for (MedicalRecord med : medicalRecordRepository.getAllData()) {
			for (Person p : livingAtThisAdress) {
				if (med.getFirstName().equals(p.getFirstName()) && med.getLastName().equals(p.getLastName())) {
					if (getTheAge(med.getBirthdate()) <= 18) {
						newChild = new childInHouseAlertDTO(p.getFirstName(), p.getLastName(),
								"" + getTheAge(med.getBirthdate()));
						livingAtThisAdressUnder18.add(newChild);
						logger.debug(newChild.getFirstName() + ", " + newChild.getLastName()
								+ "is living  at the adress " + p.getAddress() + " and is under 18");
					} else {
						livingAtThisAdressAbove18.add(p);
					}
				}
			}

		}
		
		childAlertDTO result = new childAlertDTO(livingAtThisAdressUnder18, livingAtThisAdressAbove18);

		return result;
	}

	public List<phoneAlertDTO> getListOfPhoneNumberOfPeopleLivingCloseToTheFireStation(String firestation_number) {

		List<String> adressOfFireStation = new ArrayList<>();
		List<phoneAlertDTO> listOfPhoneNumber = new ArrayList<phoneAlertDTO>();
		List<String> alreadyExistingPhone = new ArrayList<String>();
		for (FireStation fs : fireStationRepository.getAllData()) {

			if (fs.getStation().equals(firestation_number)) {
				adressOfFireStation.add(fs.getAddress());
			}
		}

		for (Person p : personRepository.getAllData()) {
			for (String ad : adressOfFireStation) {
				if (p.getAddress().equals(ad)) {
					phoneAlertDTO newItem = new phoneAlertDTO(p.getPhone());
					logger.debug(p.getFirstName() + ", " + p.getLastName() + " phone number is " + p.getPhone());

					for (phoneAlertDTO phone : listOfPhoneNumber) {
						alreadyExistingPhone.add(phone.getPhoneNumber());
					}

					if (!alreadyExistingPhone.contains(p.getPhone()) || alreadyExistingPhone == null) {
						listOfPhoneNumber.add(newItem);
					}
				}
			}
		}
		return listOfPhoneNumber;
	}

	public fireDTO getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(String address) {

		List<firePersonDTO> results = new ArrayList<firePersonDTO>();

		for (Person p : personRepository.getAllData()) {

			if (p.getAddress().equals(address)) {

				for (MedicalRecord medRec : medicalRecordRepository.getAllData()) {

					if (medRec.getFirstName().equals(p.getFirstName())
							&& medRec.getLastName().equals(p.getLastName())) {

						List<String> medRecords = new ArrayList<>();
						medRecords.addAll(medRec.getMedications());
						medRecords.addAll(medRec.getAllergies());

						int result = getTheAge(medRec.getBirthdate());
						;

						firePersonDTO newItem = new firePersonDTO(p.getFirstName(), p.getLastName(), p.getPhone(),
								"" + result, medRecords);
						results.add(newItem);

						logger.debug(p.getFirstName() + ", " + p.getLastName() + " is living at the adress " + address
								+ " and MedRecord has been found" + medRec.getAllergies() + ", posology"
								+ medRec.getMedications());

					}
				}

			}

		}

		String resultId = new String();
		for (FireStation s : fireStationRepository.getAllData()) {

			if (s.getAddress().equals(address)) {

				resultId = s.getStation();
			}

		}

		fireDTO finalResult = new fireDTO(results, resultId);
		return finalResult;
	}

	public List<floodDTO> getListOfAllAddressProtectedByTheFireStation(List<String> IDStation) {

		List<String> adressDeservedByIDStations = new ArrayList<>();
		List<floodDTO> results = new ArrayList<>();

		for (String s : IDStation) {

			for (FireStation p : fireStationRepository.getAllData()) {

				if (p.getStation().equals(s)) {
					adressDeservedByIDStations.add(p.getAddress());
				}
			}
		}

		for (String adress : adressDeservedByIDStations) {

			for (Person p : personRepository.getAllData()) {

				if (p.getAddress().equals(adress)) {

					for (MedicalRecord medRec : medicalRecordRepository.getAllData()) {

						if (medRec.getFirstName().equals(p.getFirstName())
								&& medRec.getLastName().equals(p.getLastName())) {
							List<String> newItemMedRec = new ArrayList<>();
							newItemMedRec.addAll(medRec.getMedications());
							newItemMedRec.addAll(medRec.getAllergies());
							int age = getTheAge(medRec.getBirthdate());
							floodDTO newItem = new floodDTO(p.getAddress(), p.getFirstName(), p.getLastName(),
									p.getPhone(), "" + age, newItemMedRec);
							results.add(newItem);

							logger.debug(p.getFirstName() + ", " + p.getLastName()
									+ " is protected by FireStation at the adress" + adress
									+ " with added medical Records");
						}
					}

				}
			}
		}

		return results;
	}

	public List<personInfoDTO> getMedicalInformationOfPeople(String firstName, String lastName) {

		List<personInfoDTO> result = new ArrayList<>();
		int indexSwitcher = 0;

		for (Person p : personRepository.getAllData()) {

			if (p.getLastName().equals(lastName)) {

				for (MedicalRecord medRec : medicalRecordRepository.getAllData()) {

					if (medRec.getLastName().equals(p.getLastName())
							&& medRec.getFirstName().equals(p.getFirstName())) {

						int ageItem = getTheAge(medRec.getBirthdate());
						List<String> newItemMedicalRecord = new ArrayList<String>();
						newItemMedicalRecord.addAll(medRec.getMedications());
						newItemMedicalRecord.addAll(medRec.getAllergies());
						personInfoDTO newItem = new personInfoDTO(medRec.getLastName(), medRec.getFirstName(),
								p.getAddress(), "" + ageItem, p.getEmail(), newItemMedicalRecord);
						result.add(newItem);
						logger.debug(p.getLastName() + " should be equal to " + lastName + " , member : "
								+ p.getFirstName() + " adding all the medical records ! ");
					}
				}
			}
		}

		/**
		 * This part of the code is solely to put the requested name first in the list
		 * appearing. Indeed, we set the required name first in the list appearance.
		 */

		for (int i = 0; i < result.size(); i++) {

			if (result.get(i).getLastName().equals(lastName) && result.get(i).getName().equals(firstName)) {

				indexSwitcher = i;
			}

		}

		if (indexSwitcher != 0) {

			personInfoDTO oldFirst = result.get(0);
			result.set(0, result.get(indexSwitcher));
			result.set(indexSwitcher, oldFirst);

		}

		return result;
	}

	public List<communityEmailDTO> getAllEmailFromAllInhabitantOfCity(String city) {

		ArrayList<communityEmailDTO> emailsOfCityInhabitants = new ArrayList<>();
		Boolean mailIsExisting = false;

		for (Person p : personRepository.getAllData()) {

			if (p.getCity().equals(city)) {

				communityEmailDTO newItem = new communityEmailDTO(p.getEmail());

				for (communityEmailDTO existing : emailsOfCityInhabitants) {

					if (existing.getEmail().equals(newItem.getEmail())) {

						mailIsExisting = true;
					}

				}

				logger.debug(p.getFirstName() + " , " + p.getLastName() + " email is " + p.getEmail()
						+ "and live in the city of " + p.getCity() + "should be equal to " + city);

				if (!mailIsExisting) {

					emailsOfCityInhabitants.add(newItem);

				}

			}

			mailIsExisting = false;
		}

		return emailsOfCityInhabitants;
	}

	private int getTheAge(String birthdate) {

		String[] currentAge = birthdate.split("/");
		LocalDate pastDate = LocalDate.of(Integer.parseInt(currentAge[2]), Integer.parseInt(currentAge[0]),
				Integer.parseInt(currentAge[1]));
		LocalDate nowDate = LocalDate.now();
		Period period = Period.between(pastDate, nowDate);
		int result = (int) ((period.toTotalMonths()) / 12);

		return result;

	}

	public PersonRepository getPersonRepository() {
		return personRepository;
	}

	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public FireStationRepository getFireStationRepository() {
		return fireStationRepository;
	}

	public void setFireStationRepository(FireStationRepository fireStationRepository) {
		this.fireStationRepository = fireStationRepository;
	}

	public MedicalRecordRepository getMedicalRecordRepository() {
		return medicalRecordRepository;
	}

	public void setMedicalRecordRepository(MedicalRecordRepository medicalRecordRepository) {
		this.medicalRecordRepository = medicalRecordRepository;
	}

}
