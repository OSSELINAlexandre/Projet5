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

import com.safety.savinglives.safetynetapplication.DTO.ChildAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.ChildInHouseAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.CommunityEmailDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireStationDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireStationGeneralDTO;
import com.safety.savinglives.safetynetapplication.DTO.FloodDTO;
import com.safety.savinglives.safetynetapplication.DTO.PersonInfoDTO;
import com.safety.savinglives.safetynetapplication.DTO.PhoneAlertDTO;
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

	public FireStationGeneralDTO getListOfPeopleCoveredByFireStation(String id) {

		ArrayList<String> adressCoveredByStation = new ArrayList<>();

		for (FireStation s : fireStationRepository.getAllData()) {

			if (s.getStation().equals(id)) {

				adressCoveredByStation.add(s.getAddress());
				logger.debug(s.getAddress() + " added to the adressCoveredByStation for id " + id);
			}

		}

		ArrayList<FireStationDTO> citizenLivingClose = new ArrayList<>();

		for (String s : adressCoveredByStation) {

			for (Person p : personRepository.getAllData()) {

				if (p.getAddress().equals(s)) {

					logger.debug(p.getFirstName() + ", " + p.getLastName()
							+ " supposed to live in the area of fireStation number  " + id);

					FireStationDTO newItem = new FireStationDTO(p.getFirstName(), p.getLastName(), p.getAddress(),
							p.getPhone());
					citizenLivingClose.add(newItem);

				}

			}
		}

		FireStationGeneralDTO result = new FireStationGeneralDTO(citizenLivingClose, 0, 0);

		for (FireStationDTO p : citizenLivingClose) {

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

	public ChildAlertDTO getListOfChildBasedOnAddress(String adress) {
		List<Person> livingAtThisAdress = new ArrayList<Person>();
		List<ChildInHouseAlertDTO> livingAtThisAdressUnder18 = new ArrayList<ChildInHouseAlertDTO>();
		List<Person> livingAtThisAdressAbove18 = new ArrayList<Person>();
		ChildInHouseAlertDTO newChild = new ChildInHouseAlertDTO();
		for (Person p : personRepository.getAllData()) {
			if (p.getAddress().equals(adress)) {
				livingAtThisAdress.add(p);
			}
		}

		for (MedicalRecord med : medicalRecordRepository.getAllData()) {
			for (Person p : livingAtThisAdress) {
				if (med.getFirstName().equals(p.getFirstName()) && med.getLastName().equals(p.getLastName())) {
					if (getTheAge(med.getBirthdate()) <= 18) {
						newChild = new ChildInHouseAlertDTO(p.getFirstName(), p.getLastName(),
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

		ChildAlertDTO result = new ChildAlertDTO(livingAtThisAdressUnder18, livingAtThisAdressAbove18);
		return result;
	}

	public List<PhoneAlertDTO> getListOfPhoneNumberOfPeopleLivingCloseToTheFireStation(String firestation_number) {

		List<String> adressOfFireStation = new ArrayList<>();
		List<PhoneAlertDTO> listOfPhoneNumber = new ArrayList<PhoneAlertDTO>();
		List<String> alreadyExistingPhone = new ArrayList<String>();
		for (FireStation fs : fireStationRepository.getAllData()) {

			if (fs.getStation().equals(firestation_number)) {
				adressOfFireStation.add(fs.getAddress());
			}
		}

		for (Person p : personRepository.getAllData()) {
			for (String ad : adressOfFireStation) {
				if (p.getAddress().equals(ad)) {
					PhoneAlertDTO newItem = new PhoneAlertDTO(p.getPhone());
					logger.debug(p.getFirstName() + ", " + p.getLastName() + " phone number is " + p.getPhone());

					for (PhoneAlertDTO phone : listOfPhoneNumber) {
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

	public List<FireDTO> getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(String address) {

		List<FireDTO> results = new ArrayList<FireDTO>();

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

						FireDTO newItem = new FireDTO(p.getFirstName(), p.getLastName(), p.getPhone(), "" + result,
								medRecords);
						results.add(newItem);

						logger.debug(p.getFirstName() + ", " + p.getLastName() + " is living at the adress " + address
								+ " and MedRecord has been found" + medRec.getAllergies() + ", posology"
								+ medRec.getMedications());

					}
				}

			}

		}

		return results;
	}

	public List<FloodDTO> getListOfAllAddressProtectedByTheFireStation(List<String> IDStation) {

		List<String> adressDeservedByIDStations = new ArrayList<>();
		List<FloodDTO> results = new ArrayList<>();

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
							FloodDTO newItem = new FloodDTO(p.getAddress(), p.getFirstName(), p.getLastName(),
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

	public List<PersonInfoDTO> getMedicalInformationOfPeople(String firstName, String lastName) {

		List<PersonInfoDTO> result = new ArrayList<>();
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
						PersonInfoDTO newItem = new PersonInfoDTO(medRec.getLastName(), medRec.getFirstName(),
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

			PersonInfoDTO oldFirst = result.get(0);
			result.set(0, result.get(indexSwitcher));
			result.set(indexSwitcher, oldFirst);

		}

		return result;
	}

	public List<CommunityEmailDTO> getAllEmailFromAllInhabitantOfCity(String city) {

		ArrayList<CommunityEmailDTO> emailsOfCityInhabitants = new ArrayList<>();
		Boolean mailIsExisting = false;

		for (Person p : personRepository.getAllData()) {

			if (p.getCity().equals(city)) {

				CommunityEmailDTO newItem = new CommunityEmailDTO(p.getEmail());

				for (CommunityEmailDTO existing : emailsOfCityInhabitants) {

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
