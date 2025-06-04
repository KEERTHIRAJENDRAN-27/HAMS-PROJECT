package com.hams.medical.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hams.medical.dto.MedicalHistoryPatientResponseDTO;
import com.hams.medical.dto.Patient;
import com.hams.medical.exception.CustomGlobalExceptionHandler;
import com.hams.medical.exception.HistoryNotFoundException;
import com.hams.medical.exception.PatientNotFoundException;
import com.hams.medical.feignclient.PatientClient;
import com.hams.medical.model.MedicalHistory;
import com.hams.medical.repository.MedicalHistoryRepository;

import feign.FeignException;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

	// Logger for debugging and monitoring
	private static final Logger logger = LoggerFactory.getLogger(MedicalHistoryServiceImpl.class);

	@Autowired
	private MedicalHistoryRepository repository;

	@Autowired
	private PatientClient patientClient;

	// Constructor-based injection for exception handling
	MedicalHistoryServiceImpl(CustomGlobalExceptionHandler customGlobalExceptionHandler) {
	}

	// Fetch medical history along with patient details.
	public MedicalHistoryPatientResponseDTO getMedicalHistoryWithPatient(Long patientId)
			throws PatientNotFoundException {
		logger.info("Fetching medical history for patient ID: {}", patientId);

		try {
			// Fetch patient details using Feign Client
			Patient patient = patientClient.getPatientById(patientId);
			List<MedicalHistory> history = repository.findByPatientId(patientId);

			logger.info("Medical history fetched successfully for patient ID: {}", patientId);
			return new MedicalHistoryPatientResponseDTO(patient, history);
		} catch (FeignException.NotFound ex) {
			logger.error("Patient with ID {} not found", patientId);
			throw new PatientNotFoundException("Patient with ID " + patientId + " not found in Patient Service");
		}
	}

	// * Saves medical history for a patient.
	@Override
	public String saveHistory(MedicalHistory dto) throws PatientNotFoundException {
		logger.info("Saving medical history for patient ID: {}", dto.getPatientId());

		try {
			// Validate patient ID
			Patient patient = patientClient.getPatientById(dto.getPatientId());
		} catch (FeignException.NotFound ex) {
			logger.error("Invalid Patient ID: {}", dto.getPatientId());
			throw new PatientNotFoundException("Invalid Patient ID: " + dto.getPatientId());
		}

		// Map DTO to Entity and save
		MedicalHistory history = new MedicalHistory();
		history.setPatientId(dto.getPatientId());
		history.setDiagnosis(dto.getDiagnosis());
		history.setTreatment(dto.getTreatment());
		history.setDateOfVisit(dto.getDateOfVisit());

		repository.save(history);
		logger.info("Medical history saved successfully for patient ID: {}", dto.getPatientId());
		return "Medical history saved successfully.";
	}

	// Updates an existing medical history record.
	@Override
	public String updateHistory(Long id, MedicalHistory updated) throws HistoryNotFoundException {
		logger.info("Updating medical history for ID: {}", id);

		MedicalHistory existing = repository.findById(id).orElseThrow(() -> {
			logger.error("Medical history not found for ID: {}", id);
			return new HistoryNotFoundException("History with ID " + id + " not found.");
		});

		// Update fields
		existing.setPatientId(updated.getPatientId());
		existing.setDiagnosis(updated.getDiagnosis());
		existing.setTreatment(updated.getTreatment());
		existing.setDateOfVisit(updated.getDateOfVisit());

		repository.save(existing);
		logger.info("Medical history updated successfully for ID: {}", id);
		return "Medical history updated successfully.";
	}

	// Fetch a medical history record by ID.
	@Override
	public MedicalHistory getById(Long id) throws HistoryNotFoundException {
		logger.info("Fetching medical history for ID: {}", id);

		return repository.findById(id).orElseThrow(() -> {
			logger.error("Medical history not found for ID: {}", id);
			return new HistoryNotFoundException("History with ID " + id + " not found.");
		});
	}

	// Fetch all medical history records for a specific patient.
	@Override
	public List<MedicalHistory> getByPatientId(Long patientId) throws HistoryNotFoundException {
		logger.info("Fetching medical history for patient ID: {}", patientId);

		List<MedicalHistory> list = repository.findByPatientId(patientId);
		if (list.isEmpty()) {
			logger.warn("No medical history found for patient ID: {}", patientId);
			throw new HistoryNotFoundException("No history found for Patient ID " + patientId);
		}

		return list;
	}

	// Fetch all medical history records in the system.
	@Override
	public List<MedicalHistory> getAll() {
		logger.info("Fetching all medical history records.");
		return repository.findAll();
	}

	// Deletes a medical history record by ID.
	@Override
	public String deleteById(Long id) throws HistoryNotFoundException {
		logger.info("Attempting to delete medical history for ID: {}", id);

		if (!repository.existsById(id)) {
			logger.error("Medical history not found for ID: {}", id);
			throw new HistoryNotFoundException("History with ID " + id + " not found.");
		}

		repository.deleteById(id);
		logger.info("Medical history deleted successfully for ID: {}", id);
		return "Medical history deleted successfully.";
	}
}
