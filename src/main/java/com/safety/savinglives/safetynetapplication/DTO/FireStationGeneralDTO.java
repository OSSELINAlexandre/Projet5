package com.safety.savinglives.safetynetapplication.DTO;

import java.util.List;

public class FireStationGeneralDTO {

	private List<FireStationDTO> coveredCitizens;
	private int adultCount;
	private int childCount;

	public FireStationGeneralDTO() {
		super();
	}

	public FireStationGeneralDTO(List<FireStationDTO> coveredCitizens, int adultCount, int childCount) {
		super();
		this.coveredCitizens = coveredCitizens;
		this.adultCount = adultCount;
		this.childCount = childCount;
	}

	public List<FireStationDTO> getCoveredCitizens() {
		return coveredCitizens;
	}

	public void setCoveredCitizens(List<FireStationDTO> coveredCitizens) {
		this.coveredCitizens = coveredCitizens;
	}

	public int getAdultCount() {
		return adultCount;
	}

	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	@Override
	public int hashCode() {

		int hashCodeFinal = 0;

		for (FireStationDTO list : coveredCitizens) {

			hashCodeFinal += list.getLastName().hashCode() + list.getFirstName().hashCode();

		}

		hashCodeFinal += this.adultCount + this.childCount;

		return hashCodeFinal;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FireStationGeneralDTO) {
			
			FireStationGeneralDTO itemCompare = (FireStationGeneralDTO) obj;
			
			return (itemCompare.getAdultCount() == this.adultCount) && (itemCompare.getChildCount() == this.getChildCount()) &&(itemCompare.getCoveredCitizens().hashCode() == this.getCoveredCitizens().hashCode());
			
		}
		return super.equals(obj);
	}
	
	
	
	

}
