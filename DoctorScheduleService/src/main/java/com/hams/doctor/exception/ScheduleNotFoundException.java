package com.hams.doctor.exception;

public class ScheduleNotFoundException extends RuntimeException {
	public ScheduleNotFoundException(String message) {
		super(message);
	}
}
