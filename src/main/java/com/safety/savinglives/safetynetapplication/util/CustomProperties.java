package com.safety.savinglives.safetynetapplication.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.safety.testone.testoneofsafetynet.util")
public class CustomProperties {

	private String fileLoc;

	public String getFileLoc() {
		return fileLoc;
	}

	public void setFileLoc(String fileLoc) {
		this.fileLoc = fileLoc;
	}


}
