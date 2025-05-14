package com.hams.appointment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AppointmentNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNotFound(AppointmentNotFoundException e) {
		return e.getMessage();
	}

	@ExceptionHandler(InvalidDoctorException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleInvalidDoctor(InvalidDoctorException e) {
		return e.getMessage();
	}

	@ExceptionHandler(InvalidPatientException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleInvalidPatient(InvalidPatientException e) {
		return e.getMessage();
	}

	@ExceptionHandler(DoctorNotAvailableException.class)
	public ResponseEntity<String> handleDoctorNotAvailableException(DoctorNotAvailableException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}
}