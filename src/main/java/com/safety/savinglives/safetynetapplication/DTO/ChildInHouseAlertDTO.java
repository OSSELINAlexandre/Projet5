package com.safety.savinglives.safetynetapplication.DTO;



public class ChildInHouseAlertDTO {

	private String firstName;
	private String lastName;
	private String age;

	public ChildInHouseAlertDTO(String firstName, String lastName, String age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;

	}

	public ChildInHouseAlertDTO() {
		super();
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

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public int hashCode() {

		return firstName.hashCode() + lastName.hashCode() + age.hashCode();
	}
	
	
	

}
