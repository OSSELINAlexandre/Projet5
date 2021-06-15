package com.safety.savinglives.safetynetapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safety.savinglives.safetynetapplication.repository.GeneralDataRepository;
import com.safety.savinglives.safetynetapplication.util.CustomProperties;

@SpringBootApplication
public class TestoneofsafetynetApplication implements CommandLineRunner {

	@Autowired
	CustomProperties cp;

	@Autowired
	GeneralDataRepository generalDataRepository;

	@Autowired
	com.safety.savinglives.safetynetapplication.repository.DAOFactory DAOFactory;

	public static void main(String[] args) {
		SpringApplication.run(TestoneofsafetynetApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		generalDataRepository = DAOFactory.loadDataFromFile();
	}

}
