package com.safety.savinglives.safetynetapplication.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safety.savinglives.safetynetapplication.DTO.FireDTO;
import com.safety.savinglives.safetynetapplication.DTO.FirePersonDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireStationDTO;
import com.safety.savinglives.safetynetapplication.DTO.FireStationGeneralDTO;
import com.safety.savinglives.safetynetapplication.DTO.FloodDTO;
import com.safety.savinglives.safetynetapplication.DTO.PhoneAlertDTO;
import com.safety.savinglives.safetynetapplication.model.FireStation;
import com.safety.savinglives.safetynetapplication.model.MedicalRecord;
import com.safety.savinglives.safetynetapplication.model.Person;
import com.safety.savinglives.safetynetapplication.repository.FireStationRepository;
import com.safety.savinglives.safetynetapplication.repository.MedicalRecordRepository;
import com.safety.savinglives.safetynetapplication.repository.PersonRepository;

@Service
public class FireStationServices {
	
	private static final Logger logger = LogManager.getLogger(FireStationServices.class);


	@Autowired
	FireStationRepository fireStationRepository;
	
	@Autowired
	MedicalRecordRepository medicalRecordRepository;
	
	
	@Autowired
	PersonRepository personRepository;

	public FireStationServices() {
		super();
	}

	public Iterable<FireStation> getAllFireStations() {

		return fireStationRepository.getAllData();
	}

	public Boolean saveANewStation(FireStation caserne) {
		
		
		
		logger.info("µµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµµ");
		return fireStationRepository.save(caserne);
	}

	public Boolean deleteANewStation(String address) {

		for (FireStation fs : fireStationRepository.getAllData()) {

			if (fs.getAddress().equals(address)) {

				return fireStationRepository.delete(fs);
			}

		}

		return false;
	}

	public FireStation updateAStation(String address, String newId) {

		List<FireStation> copyFireStationList = fireStationRepository.getAllData();
		FireStation newStation = null;

		for (FireStation fs : copyFireStationList) {

			for (int i = 0; i < copyFireStationList.size(); i++) {

				if (copyFireStationList.get(i).getAddress().equals(address)) {

					newStation = copyFireStationList.get(i);
					newStation.setStation(newId);
					fireStationRepository.update(i, newStation);
				}
			}
		}

		return newStation;
	}

	public List<FireStation> selectSomeFireStation(String stationNumber) {

		Iterable<FireStation> wholeThing = getAllFireStations();
		List<FireStation> resultThing = new ArrayList<FireStation>();
		for (FireStation fs : wholeThing) {

			if (fs.getStation().equals(stationNumber)) {
				resultThing.add(fs);
			}

		}

		return resultThing;
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

	
	public FireDTO getListOfInhabitantAndPhoneNumberOfFireStationCloseBy(String address) {

		List<FirePersonDTO> results = new ArrayList<FirePersonDTO>();

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

						FirePersonDTO newItem = new FirePersonDTO(p.getFirstName(), p.getLastName(), p.getPhone(),
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

		FireDTO finalResult = new FireDTO(results, resultId);
		return finalResult;
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
	
	
	private int getTheAge(String birthdate) {

		String[] currentAge = birthdate.split("/");
		LocalDate pastDate = LocalDate.of(Integer.parseInt(currentAge[2]), Integer.parseInt(currentAge[0]),
				Integer.parseInt(currentAge[1]));
		LocalDate nowDate = LocalDate.now();
		Period period = Period.between(pastDate, nowDate);
		int result = (int) ((period.toTotalMonths()) / 12);

		return result;

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

	public PersonRepository getPersonRepository() {
		return personRepository;
	}

	public void setPersonRepository(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}
	
	
	

}
