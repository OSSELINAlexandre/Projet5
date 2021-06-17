package com.safety.savinglives.safetynetapplication.DTO;

import java.util.List;

public class FireDTO {

	private List<FirePersonDTO> citizenLivingAtTheAddress;
	private String fireStationNumberCoveringThisAddress;

	public FireDTO(List<FirePersonDTO> citizenLivingAtTheAdress, String stationNumber) {
		super();
		this.citizenLivingAtTheAddress = citizenLivingAtTheAdress;
		this.fireStationNumberCoveringThisAddress = stationNumber;
	}

	public List<FirePersonDTO> getCitizenLivingAtTheAddress() {
		return citizenLivingAtTheAddress;
	}

	public void setCitizenLivingAtTheAddress(List<FirePersonDTO> citizenLivingAtTheAddress) {
		this.citizenLivingAtTheAddress = citizenLivingAtTheAddress;
	}

	public String getFireStationNumberCoveringThisAddress() {
		return fireStationNumberCoveringThisAddress;
	}

	public void setFireStationNumberCoveringThisAddress(String fireStationNumberCoveringThisAddress) {
		this.fireStationNumberCoveringThisAddress = fireStationNumberCoveringThisAddress;
	}

	@Override
	public int hashCode() {
		int finalHash = 0;
		for (FirePersonDTO s : citizenLivingAtTheAddress) {

			finalHash += s.hashCode();

		}

		return fireStationNumberCoveringThisAddress.hashCode() + finalHash;
	}

}
