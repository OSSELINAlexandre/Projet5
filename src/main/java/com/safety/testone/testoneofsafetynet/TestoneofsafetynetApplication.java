package com.safety.testone.testoneofsafetynet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safety.testone.testoneofsafetynet.repository.GeneralDataRepository;

@SpringBootApplication
public class TestoneofsafetynetApplication implements CommandLineRunner {

	@Autowired
	CustomProperties cp;

	@Autowired
	GeneralDataRepository generalDataRepository;

	@Autowired
	com.safety.testone.testoneofsafetynet.repository.DAOFactory DAOFactory;

	public static void main(String[] args) {
		SpringApplication.run(TestoneofsafetynetApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		generalDataRepository = DAOFactory.loadDataFromFile();
	}

}
