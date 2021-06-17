package com.safety.savinglives.safetynetapplication.DTO;

public class CoveragePersonOfAStationDTO {

	private String stationnumber;
	private String name;
	private String lastName;
	private String adress;
	private String phoneNumber;

	public CoveragePersonOfAStationDTO(String stationnumber, String name, String lastName, String adress,
			String phoneNumber) {
		super();
		this.stationnumber = stationnumber;
		this.name = name;
		this.lastName = lastName;
		this.adress = adress;
		this.phoneNumber = phoneNumber;
	}

	public String getStationnumber() {
		return stationnumber;
	}

	public void setStationnumber(String stationnumber) {
		this.stationnumber = stationnumber;
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

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
