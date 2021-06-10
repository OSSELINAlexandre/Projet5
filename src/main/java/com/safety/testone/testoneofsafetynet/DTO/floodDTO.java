package com.safety.testone.testoneofsafetynet.DTO;

import java.util.List;

public class FloodDTO {

	private String address;
	private String name;
	private String lastName;
	private String phoneNumber;
	private String age;
	private List<String> medicalRecords;

	public FloodDTO(String address, String name, String lastName, String phoneNumber, String age,
			List<String> medicalRecords) {
		super();
		this.address = address;
		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.medicalRecords = medicalRecords;
	}

	public FloodDTO() {
		super();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public List<String> getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(List<String> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}

}
