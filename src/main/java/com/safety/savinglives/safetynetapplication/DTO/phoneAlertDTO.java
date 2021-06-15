package com.safety.savinglives.safetynetapplication.DTO;

public class phoneAlertDTO {

	private String phoneNumber;

	public phoneAlertDTO(String phoneNumber) {
		super();
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int hashCode() {

		return phoneNumber.hashCode();
	}

}
