package com.hams.patient.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hams.patient.exception.PatientNotFoundException;
import com.hams.patient.model.Patient;
import com.hams.patient.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

	private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Autowired
	private PatientRepository repository;

	@Override
	public String registerPatient(Patient patient) {
		logger.info("Registering new patient with name: {}", patient.getName());
		repository.save(patient);
		logger.info("Patient registered successfully.");
		return "Patient registered successfully.";
	}

	@Override
	public String updatePatient(Long patientId, Patient updatedPatient) {
		logger.info("Updating patient with ID: {}", patientId);

		Patient existing = repository.findById(patientId).orElseThrow(() -> {
			logger.error("Patient not found with ID: {}", patientId);
			return new PatientNotFoundException("Patient with ID " + patientId + " not found.");
		});

		existing.setName(updatedPatient.getName());
		existing.setAge(updatedPatient.getAge());
		existing.setBloodGroup(updatedPatient.getBloodGroup());
		existing.setGender(updatedPatient.getGender());
		existing.setDateOfBirth(updatedPatient.getDateOfBirth());
		existing.setMedicalHistory(updatedPatient.getMedicalHistory());
		existing.setEmail(updatedPatient.getEmail());
		existing.setContactDetails(updatedPatient.getContactDetails());

		repository.save(existing);
		logger.info("Patient with ID {} updated successfully.", patientId);
		return "Patient updated successfully.";
	}

	@Override
	public Patient getPatientById(Long id) {
		logger.info("Fetching patient details for ID: {}", id);
		return repository.findById(id).orElseThrow(() -> {
			logger.error("Patient not found with ID: {}", id);
			return new PatientNotFoundException("Patient with ID " + id + " not found.");
		});
	}

	@Override
	public List<Patient> getAllPatients() {
		logger.info("Fetching all patients.");
		return repository.findAll();
	}

	@Override
	public String deletePatient(Long id) {
		logger.info("Attempting to delete patient with ID: {}", id);

		if (!repository.existsById(id)) {
			logger.error("Patient not found with ID: {}", id);
			throw new PatientNotFoundException("Patient with ID " + id + " not found.");
		}

		repository.deleteById(id);
		logger.info("Patient with ID {} deleted successfully.", id);
		return "Patient with ID " + id + " has been deleted successfully.";
	}
}
