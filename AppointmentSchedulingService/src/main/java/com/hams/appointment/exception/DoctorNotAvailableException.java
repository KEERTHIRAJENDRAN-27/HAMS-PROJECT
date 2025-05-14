package com.hams.appointment.exception;

public class DoctorNotAvailableException extends RuntimeException {
	public DoctorNotAvailableException(String message) {
		super(message);
	}
}