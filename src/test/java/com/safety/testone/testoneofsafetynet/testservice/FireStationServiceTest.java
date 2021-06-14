package com.safety.testone.testoneofsafetynet.testservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safety.testone.testoneofsafetynet.model.FireStation;
import com.safety.testone.testoneofsafetynet.model.MedicalRecord;
import com.safety.testone.testoneofsafetynet.model.Person;
import com.safety.testone.testoneofsafetynet.repository.FireStationRepository;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {

	@Mock
	static FireStationRepository fs;

	static ArrayList<FireStation> falseList;
	
	static ArrayList<FireStation> mockUpFireStation = new ArrayList<FireStation>();
	static ArrayList<Person> mockUpPerson= new ArrayList<Person>();
	static ArrayList<MedicalRecord> mockUpMedicalRecords= new ArrayList<MedicalRecord>();

	static {
		
		mockUpFireStation.add(new FireStation("32 rue du chemin", "1" ));
		mockUpFireStation.add( new FireStation("1 rue de la paix", "2" ));
		mockUpFireStation.add( new FireStation("78 bis avenue de la lumière", "1" ));
		mockUpFireStation.add( new FireStation("Rue général lousin", "2"));
		
		 mockUpPerson.add(new Person("Alex", "Osselin", "32 rue du chemin", "Meaux", "54985", "888-888-888", "codeurjava@gmail.com"));
		 mockUpPerson.add(new Person("Bernard", "Arnaud", "1 rue de la paix", "Paris", "75000", "888-888-888", "bernard.arnaud@lvmh.com"));
		 mockUpPerson.add(new Person("Sophie", "Lecomte", "78 bis avenue de la lumière", "Meaux", "54985", "888-789-888", "sophie.Lecomte@gmail.com"));
		 mockUpPerson.add(new Person("Christine", "Cain", "Rue général lousin", "Paris", "75000", "765-888-888", "cain.cain@gmail.com"));
	 
		 mockUpMedicalRecords.add(new MedicalRecord("Alex", "Osselin" , "11/06/1995", null , null));
		 mockUpMedicalRecords.add(new MedicalRecord("Bernard", "Arnaud" , "01/06/1955", null , null));
		 mockUpMedicalRecords.add(new MedicalRecord("Sophie", "Lecomte" , "05/06/2000", null , null));
		 mockUpMedicalRecords.add(new MedicalRecord("Christine", "Cain" , "28/12/2007", null , null));
	}

	@BeforeAll
	private static void init() {
		
		
		List<String> fakeMeds = new ArrayList<String>();
		List<String> falseAllergies = new ArrayList<String>();
		fakeMeds.add("dolliprane : 250mg");
		fakeMeds.add("Ibuprofène : 200mg");
		falseAllergies.add("kryptonite");
		
		
		when(fs.getAllData()).thenReturn(mockUpFireStation);
		

	}

	@Test
	void testGetAllFireStations() {

		List<FireStation> expected = new ArrayList<>();
		FireStation f1 = new FireStation("Jump street", "1");
		FireStation f2 = new FireStation("Jump street", "2");
		expected.add(f1);
		expected.add(f2);

		assertTrue(expected.containsAll(falseList));

	}

}
