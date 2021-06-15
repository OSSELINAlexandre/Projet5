package com.safety.savinglives.safetynetapplication.DTO;

import java.util.List;

public class fireStationGeneralDTO {

	private List<fireStationDTO> coveredCitizens;
	private int adultCount;
	private int childCount;

	public fireStationGeneralDTO() {
		super();
	}

	public fireStationGeneralDTO(List<fireStationDTO> coveredCitizens, int adultCount, int childCount) {
		super();
		this.coveredCitizens = coveredCitizens;
		this.adultCount = adultCount;
		this.childCount = childCount;
	}

	public List<fireStationDTO> getCoveredCitizens() {
		return coveredCitizens;
	}

	public void setCoveredCitizens(List<fireStationDTO> coveredCitizens) {
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

		for (fireStationDTO list : coveredCitizens) {

			hashCodeFinal += list.getLastName().hashCode() + list.getFirstName().hashCode();

		}

		hashCodeFinal += this.adultCount + this.childCount;

		return hashCodeFinal;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof fireStationGeneralDTO) {
			
			fireStationGeneralDTO itemCompare = (fireStationGeneralDTO) obj;
			
			return (itemCompare.getAdultCount() == this.adultCount) && (itemCompare.getChildCount() == this.getChildCount()) &&(itemCompare.getCoveredCitizens().hashCode() == this.getCoveredCitizens().hashCode());
			
		}
		return super.equals(obj);
	}
	
	
	
	

}
