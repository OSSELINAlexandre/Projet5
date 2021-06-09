package com.safety.testone.testoneofsafetynet.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.testone.testoneofsafetynet.DAO.FireStationDAO;
import com.safety.testone.testoneofsafetynet.DAO.MedicalRecordDAO;
import com.safety.testone.testoneofsafetynet.DAO.PersonDAO;
import com.safety.testone.testoneofsafetynet.DTO.childAlertDTO;
import com.safety.testone.testoneofsafetynet.DTO.communityEmailDTO;
import com.safety.testone.testoneofsafetynet.DTO.fireDTO;
import com.safety.testone.testoneofsafetynet.DTO.floodDTO;
import com.safety.testone.testoneofsafetynet.DTO.personInfoDTO;
import com.safety.testone.testoneofsafetynet.DTO.phoneAlertDTO;
import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.MedicalRecord;
import com.safety.testone.testoneofsafetynet.model.Person;

@Service
public class URLService {
	@Autowired
	PersonDAO personDAO;

	@Autowired
	FireStationDAO fireStationDAO;

	@Autowired
	MedicalRecordDAO medicalRecordDAO;

	public URLService() {
		super();
	}

	public List<childAlertDTO> getListOfChildBasedOnAddress(String adress) {
		List<Person> livingAtThisAdress = new ArrayList<Person>();
		List<childAlertDTO> livingAtThisAdressUnder18 = new ArrayList<childAlertDTO>();
		for (Person p : personDAO.getAllPersons()) {
			if (p.getAddress().equals(adress)) {
				livingAtThisAdress.add(p);
			}
		}

		for (MedicalRecord med : medicalRecordDAO.getAllMedicalRecords()) {
			for (Person p : livingAtThisAdress) {
				if (med.getFirstName().equals(p.getFirstName()) && med.getLastName().equals(p.getLastName())) {
					String date = med.getBirthdate();
					String[] formatter = date.split("/");
					LocalDate pastDate = LocalDate.of(Integer.parseInt(formatter[2]), Integer.parseInt(formatter[0]),
							Integer.parseInt(formatter[1]));
					LocalDate nowDate = LocalDate.now();
					Period period = Period.between(pastDate, nowDate);
					Long result = period.toTotalMonths();
					if (result <= 216) {
						childAlertDTO newChild = new childAlertDTO(p.getFirstName(), p.getLastName(),
								med.getBirthdate());
						livingAtThisAdressUnder18.add(newChild);
					}
				}
			}
		}
		return livingAtThisAdressUnder18;
	}

	public List<phoneAlertDTO> getListOfPhoneNumberOfPeopleLivingCloseToTheFireStation(String firestation_number) {

		List<String> adressOfFireStation = new ArrayList<>();
		List<phoneAlertDTO> listOfPhoneNumber = new ArrayList<phoneAlertDTO>();
		List<String> alreadyExistingPhone = new ArrayList<String>();
		for (FireStation fs : fireStationDAO.getAllFireStations()) {

			if (fs.getStation().equals(firestation_number)) {
				adressOfFireStation.add(fs.getAddress());
			}
		}

		for (Person p : personDAO.getAllPersons()) {
			for (String ad : adressOfFireStation) {
				if (p.getAddress().equals(ad)) {
					phoneAlertDTO newItem = new phoneAlertDTO(p.getPhone());

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

	public List<fireDTO> getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(String address) {

		List<fireDTO> results = new ArrayList<fireDTO>();

		for (Person p : personDAO.getAllPersons()) {

			if (p.getAddress().equals(address)) {

				for (MedicalRecord medRec : medicalRecordDAO.getAllMedicalRecords()) {

					if (medRec.getFirstName().equals(p.getFirstName())
							&& medRec.getLastName().equals(p.getLastName())) {

						List<String> medRecords = new ArrayList<>();
						medRecords.addAll(medRec.getMedications());
						medRecords.addAll(medRec.getAllergies());

						int result = getTheAge(medRec.getBirthdate());
						;

						fireDTO newItem = new fireDTO(p.getFirstName(), p.getLastName(), p.getPhone(), "" + result,
								medRecords);
						results.add(newItem);
					}
				}

			}

		}

		return results;
	}

	public List<floodDTO> getListOfAllAddressProtectedByTheFireStation(List<String> IDStation) {

		List<String> adressDeservedByIDStations = new ArrayList<>();
		List<floodDTO> results = new ArrayList<>();

		for (String s : IDStation) {

			for (FireStation p : fireStationDAO.getAllFireStations()) {

				if (p.getStation().equals(s)) {
					adressDeservedByIDStations.add(p.getAddress());
				}
			}
		}

		for (String adress : adressDeservedByIDStations) {

			for (Person p : personDAO.getAllPersons()) {

				if (p.getAddress().equals(adress)) {

					for (MedicalRecord medRec : medicalRecordDAO.getAllMedicalRecords()) {

						if (medRec.getFirstName().equals(p.getFirstName())
								&& medRec.getLastName().equals(p.getLastName())) {
							List<String> newItemMedRec = new ArrayList<>();
							newItemMedRec.addAll(medRec.getMedications());
							newItemMedRec.addAll(medRec.getAllergies());
							int age = getTheAge(medRec.getBirthdate());
							floodDTO newItem = new floodDTO(p.getAddress(), p.getFirstName(), p.getLastName(),
									p.getPhone(), "" + age, newItemMedRec);
							results.add(newItem);
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

		for (Person p : personDAO.getAllPersons()) {

			if (p.getLastName().equals(lastName)) {

				for (MedicalRecord medRec : medicalRecordDAO.getAllMedicalRecords()) {

					if (medRec.getLastName().equals(p.getLastName())
							&& medRec.getFirstName().equals(p.getFirstName())) {

						int ageItem = getTheAge(medRec.getBirthdate());
						List<String> newItemMedicalRecord = new ArrayList<String>();
						newItemMedicalRecord.addAll(medRec.getMedications());
						newItemMedicalRecord.addAll(medRec.getAllergies());
						personInfoDTO newItem = new personInfoDTO(medRec.getLastName(), medRec.getFirstName(),
								p.getAddress(), "" + ageItem, p.getEmail(), newItemMedicalRecord);
						result.add(newItem);
					}
				}
			}
		}

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

		for (Person p : personDAO.getAllPersons()) {

			if (p.getCity().equals(city)) {

				communityEmailDTO newItem = new communityEmailDTO(p.getEmail());

				for (communityEmailDTO existing : emailsOfCityInhabitants) {

					if (existing.getEmail().equals(newItem.getEmail())) {

						mailIsExisting = true;
					}

				}

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

}
