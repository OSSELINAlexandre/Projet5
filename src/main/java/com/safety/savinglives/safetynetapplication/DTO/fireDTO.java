package com.safety.savinglives.safetynetapplication.DTO;

import java.util.List;

public class FireDTO {

	private String name;
	private String lastName;
	private String phoneNumber;
	private String age;
	private List<String> medRecords;

	public FireDTO() {
		super();
	}

	public FireDTO( String name, String lastName, String phoneNumber, String age, List<String> medRecords) {
		super();

		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.medRecords = medRecords;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public List<String> getMedRecords() {
		return medRecords;
	}

	public void setMedRecords(List<String> medRecords) {
		this.medRecords = medRecords;
	}

}
