package com.safety.testone.testoneofsafetynet.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@ResponseBody
public class ChildAlertException extends RuntimeException {

	public ChildAlertException(String string) {
		super(string);
	}

}
