package com.safety.savinglives.safetynetapplication.DTO;

import java.util.List;

import com.safety.savinglives.safetynetapplication.model.Person;

public class childAlertDTO {

	private List<childInHouseAlertDTO> childInTheHouse;
	private List<Person> AdultInTheHouse;

	public childAlertDTO(List<childInHouseAlertDTO> childInTheHouse, List<Person> adultInTheHouse) {
		super();
		this.childInTheHouse = childInTheHouse;
		AdultInTheHouse = adultInTheHouse;
	}

	public childAlertDTO() {
		super();
	}

	public List<childInHouseAlertDTO> getChildInTheHouse() {
		return childInTheHouse;
	}

	public void setChildInTheHouse(List<childInHouseAlertDTO> childInTheHouse) {
		this.childInTheHouse = childInTheHouse;
	}

	public List<Person> getAdultInTheHouse() {
		return AdultInTheHouse;
	}

	public void setAdultInTheHouse(List<Person> adultInTheHouse) {
		AdultInTheHouse = adultInTheHouse;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof childAlertDTO) {

			return this.hashCode() == obj.hashCode();

		}
		return false;

	}

	@Override
	public int hashCode() {

		int finalHash = 0;

		for (childInHouseAlertDTO cad : childInTheHouse) {

			finalHash += cad.hashCode();

		}

		for (Person p : AdultInTheHouse) {

			finalHash += p.hashCode();
		}

		return finalHash;
	}

}
