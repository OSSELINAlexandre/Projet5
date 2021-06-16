package com.safety.savinglives.safetynetapplication.DTO;

import java.util.List;

public class fireDTO {

	private List<firePersonDTO> citizenLivingAtTheAddress;
	private String fireStationNumberCoveringThisAddress;

	public fireDTO(List<firePersonDTO> citizenLivingAtTheAdress, String stationNumber) {
		super();
		this.citizenLivingAtTheAddress = citizenLivingAtTheAdress;
		this.fireStationNumberCoveringThisAddress = stationNumber;
	}

	public List<firePersonDTO> getCitizenLivingAtTheAddress() {
		return citizenLivingAtTheAddress;
	}

	public void setCitizenLivingAtTheAddress(List<firePersonDTO> citizenLivingAtTheAddress) {
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
		for (firePersonDTO s : citizenLivingAtTheAddress) {

			finalHash += s.hashCode();

		}

		return fireStationNumberCoveringThisAddress.hashCode() + finalHash;
	}

}
