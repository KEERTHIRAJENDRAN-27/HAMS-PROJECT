package com.hams.doc.exception;

import java.util.Optional;

import com.hams.doc.model.DoctorSchedule;

public class DoctorAlreadyExistsException extends RuntimeException {
	public DoctorAlreadyExistsException(String message) {
		super(message);
	}

	public static Optional<DoctorSchedule> findById(Long doctorId) {
		// TODO Auto-generated method stub
		return null;
	}
}