package com.hams.doctor.exception;
 
public class InvalidDayException extends RuntimeException {
    public InvalidDayException(String message) {
        super(message);
    }
}