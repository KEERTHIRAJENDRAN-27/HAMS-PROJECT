package com.hams.appointment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AppointmentNotFoundException.class)
	public String handleNotFound(AppointmentNotFoundException ex) {
		return ex.getMessage();
	}

	@ExceptionHandler(InvalidPatientException.class)
	public String handleInvalidPatientException(InvalidPatientException ex) {
		return ex.getMessage();
	}

	@ExceptionHandler(InvalidDoctorException.class)
	public ResponseEntity<String> handleInvalidDoctorException(InvalidDoctorException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
