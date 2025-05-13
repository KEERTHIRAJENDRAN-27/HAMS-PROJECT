package com.hams.medical.exception;

public class HistoryNotFoundException extends RuntimeException {
	public HistoryNotFoundException(String message) {
		super(message);
	}
}
