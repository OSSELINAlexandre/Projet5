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
import com.safety.testone.testoneofsafetynet.repository.FireStationRepository;

@Disabled
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class FireStationServiceTest {

	@Mock
	static FireStationRepository fs;

	static ArrayList<FireStation> falseList;

	@BeforeAll
	private static void init() {
		fs = new FireStationRepository();
		FireStation f1 = new FireStation("Jump street", "1");
		FireStation f2 = new FireStation("Jump street", "2");
		falseList = new ArrayList<>();
		falseList.add(f1);
		falseList.add(f2);

		when(fs.getAllData()).thenReturn(falseList);

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
