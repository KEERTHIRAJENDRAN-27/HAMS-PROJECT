package com.hams.appointment.exception;

public class AppointmentNotFoundException extends RuntimeException {
	public AppointmentNotFoundException(String msg) {
		super(msg);
	}
}
