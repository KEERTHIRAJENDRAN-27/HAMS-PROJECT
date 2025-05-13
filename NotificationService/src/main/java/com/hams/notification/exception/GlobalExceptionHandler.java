package com.hams.notification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PatientIDNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handlePatientIDNotFound(PatientIDNotFoundException ex) {
		return ex.getMessage();
	}
}
