package com.safety.savinglives.safetynetapplication.DTO;

public class PhoneAlertDTO {

	private String phoneNumber;

	public PhoneAlertDTO(String phoneNumber) {
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
