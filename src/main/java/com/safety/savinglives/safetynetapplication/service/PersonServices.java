package com.safety.savinglives.safetynetapplication.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.savinglives.safetynetapplication.DTO.ChildAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.ChildInHouseAlertDTO;
import com.safety.savinglives.safetynetapplication.DTO.CommunityEmailDTO;
import com.safety.savinglives.safetynetapplication.DTO.PersonInfoDTO;
import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.repository.FireStationRepository;
import com.safety.savinglives.safetynetapplication.repository.MedicalRecordRepository;
import com.safety.savinglives.safetynetapplication.repository.PersonRepository;

@Service
public class PersonServices {

	private static final Logger logger = LogManager.getLogger(PersonServices.class);

	@Autowired
	PersonRepository personRepository;

	@Autowired
	FireStationRepository fireStationRepository;

	@Autowired
	MedicalRecordRepository medicalRecordRepository;

	public PersonServices() {
		super();
	}

	public Iterable<Person> getAllPersons() {

		return personRepository.getAllData();
	}

	public Boolean saveANewPerson(Person person) {

		return personRepository.save(person);
	}

	public Boolean deleteAPerson(String firstName, String lastName) {

		for (Person p : personRepository.getAllData()) {

			if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {

				return personRepository.delete(p);

			}
		}

		return false;
	}

	public Person updateAPerson(Person p) {

		Person result = null;
		List<Person> copyOfOriginial = personRepository.getAllData();
		for (int i = 0; i < copyOfOriginial.size(); i++) {

			if (copyOfOriginial.get(i).getFirstName().equals(p.getFirstName())
					&& copyOfOriginial.get(i).getLastName().equals(p.getLastName())) {
				result = personRepository.update(i, p);

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

	private int getTheAge(String birthdate) {

		String[] currentAge = birthdate.split("/");
		LocalDate pastDate = LocalDate.of(Integer.parseInt(currentAge[2]), Integer.parseInt(currentAge[0]),
				Integer.parseInt(currentAge[1]));
		LocalDate nowDate = LocalDate.now();
		Period period = Period.between(pastDate, nowDate);
		int result = (int) ((period.toTotalMonths()) / 12);

		return result;

	}


}
