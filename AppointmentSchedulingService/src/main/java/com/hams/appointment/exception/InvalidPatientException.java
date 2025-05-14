package com.hams.appointment.exception;

public class InvalidPatientException extends RuntimeException {
	public InvalidPatientException(String message) {
		super(message);
	}
}