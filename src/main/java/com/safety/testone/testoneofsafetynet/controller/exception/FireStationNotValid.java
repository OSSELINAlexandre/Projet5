package com.safety.testone.testoneofsafetynet.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FireStationNotValid extends Throwable {

	public FireStationNotValid(String string) {
		super(string);
	}

}
