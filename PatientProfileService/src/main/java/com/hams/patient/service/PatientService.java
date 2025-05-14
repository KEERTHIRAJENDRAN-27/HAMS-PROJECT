package com.hams.patient.service;

import java.util.List;

import com.hams.patient.exception.PatientNotFoundException;
import com.hams.patient.model.Patient;

public interface PatientService {
	public abstract String registerPatient(Patient patient);

	public abstract String updatePatient(Long patientId, Patient patient) throws PatientNotFoundException;

	public abstract Patient getPatientById(Long patientId) throws PatientNotFoundException;

	public abstract List<Patient> getAllPatients();

	public abstract String deletePatient(Long patientId) throws PatientNotFoundException;

}
