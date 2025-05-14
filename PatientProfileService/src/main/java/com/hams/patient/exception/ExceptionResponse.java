package com.hams.patient.exception;

import java.time.LocalDateTime;

//A response model for returning structured error details in API responses.
public class ExceptionResponse {

	private int status;
	private String message;
	private LocalDateTime time;

	public ExceptionResponse() {
		super();
	}

	// parameterized constructs an ExceptionResponse with status, message, and
	// timeStamp.
	public ExceptionResponse(int status, String message, LocalDateTime time) {
		super();
		this.status = status;
		this.message = message;
		this.time = time;
	}

	// Getters and setters
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}