package com.hams.patient.service;

import java.util.List;

import com.hams.patient.model.Patient;

public interface PatientService {
	public abstract String registerPatient(Patient patient);

	public abstract String updatePatient(Long id, Patient patient);

	public abstract Patient getPatientById(Long id);

	public abstract List<Patient> getAllPatients();

	public abstract String deletePatient(Long id);

}
