package com.safety.testone.testoneofsafetynet.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.safety.testone.testoneofsafetynet.CustomProperties;

@Component
public class PersonDAO {

	@Autowired
	CustomProperties customProperties;

	public Object getAllValues() {

		String baseApiUrl = customProperties.getApiUrl();

		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.getForObject(baseApiUrl, Object.class);
	}

}
