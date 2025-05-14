package com.hams.patient.service;

import java.util.List;

import com.hams.patient.model.Patient;

public interface PatientService {
	public abstract String registerPatient(Patient patient);

	public abstract String updatePatient(Long patientId, Patient patient);

	public abstract Patient getPatientById(Long patientId);

	public abstract List<Patient> getAllPatients();

	public abstract String deletePatient(Long patientId);

}
