package com.hams.medical.exception;
 
import java.util.Date;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
 
@ControllerAdvice
public class CustomGlobalExceptionHandler {
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date());
		// Get all errors
		ex.getBindingResult().getAllErrors().forEach(error -> {
				body.put(((FieldError)error).getField(),error.getDefaultMessage());
			});
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = PatientNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleAdminRegistrationException(PatientNotFoundException exception,
			WebRequest webRequest) {
 
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setTime(LocalDateTime.now());
		exceptionResponse.setMessage(exception.getMessage());
 
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
	}
 
	@ExceptionHandler(value = HistoryNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleAdminRegistrationException(HistoryNotFoundException exception,
			WebRequest webRequest) {
 
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setTime(LocalDateTime.now());
		exceptionResponse.setMessage(exception.getMessage());
 
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponse> handleAccountIdException(Exception exception, WebRequest webRequest) {
 
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setTime(LocalDateTime.now());
		exceptionResponse.setMessage(exception.getMessage());
 
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
 
	}
 
}