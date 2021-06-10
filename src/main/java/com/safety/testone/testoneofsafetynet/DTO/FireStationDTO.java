package com.safety.testone.testoneofsafetynet.DTO;

public class FireStationDTO {

	private String firstName;
	private String lastName;
	private String address;
	private String number;

	public FireStationDTO() {
		super();
	}

	public FireStationDTO(String firstName, String name, String address, String number) {
		super();
		this.firstName = firstName;
		this.lastName = name;
		this.address = address;
		this.number = number;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String name) {
		this.lastName = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
