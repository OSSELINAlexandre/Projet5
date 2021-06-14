package com.safety.savinglives.safetynetapplication.DTO;

import java.util.List;

import com.safety.savinglives.safetynetapplication.model.Person;

public class ChildAlertDTO {

	
	private List<ChildInHouseAlertDTO> childInTheHouse;
	private List<Person> AdultInTheHouse;
	public ChildAlertDTO(List<ChildInHouseAlertDTO> childInTheHouse, List<Person> adultInTheHouse) {
		super();
		this.childInTheHouse = childInTheHouse;
		AdultInTheHouse = adultInTheHouse;
	}
	public ChildAlertDTO() {
		super();
	}
	public List<ChildInHouseAlertDTO> getChildInTheHouse() {
		return childInTheHouse;
	}
	public void setChildInTheHouse(List<ChildInHouseAlertDTO> childInTheHouse) {
		this.childInTheHouse = childInTheHouse;
	}
	public List<Person> getAdultInTheHouse() {
		return AdultInTheHouse;
	}
	public void setAdultInTheHouse(List<Person> adultInTheHouse) {
		AdultInTheHouse = adultInTheHouse;
	}
	
	
}
