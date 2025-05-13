package com.hams.medical.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hams.medical.dto.MedicalHistoryPatientResponseDTO;
import com.hams.medical.dto.Patient;
import com.hams.medical.exception.HistoryNotFoundException;
import com.hams.medical.exception.PatientNotFoundException;
import com.hams.medical.feignclient.PatientClient;
import com.hams.medical.model.MedicalHistory;
import com.hams.medical.repository.MedicalHistoryRepository;

import feign.FeignException;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

	private static final Logger logger = LoggerFactory.getLogger(MedicalHistoryServiceImpl.class);

	@Autowired
	private MedicalHistoryRepository repository;

	@Autowired
	private PatientClient patientClient;

	public MedicalHistoryPatientResponseDTO getMedicalHistoryWithPatient(Long patientId) {
		logger.info("Fetching medical history for patient ID: {}", patientId);

		try {
			Patient patient = patientClient.getPatientById(patientId); // Feign call
			List<MedicalHistory> history = repository.findByPatientId(patientId);
			logger.info("Medical history fetched successfully for patient ID: {}", patientId);
			return new MedicalHistoryPatientResponseDTO(patient, history);
		} catch (FeignException.NotFound ex) {
			logger.error("Patient with ID {} not found", patientId);
			throw new PatientNotFoundException("Patient with ID " + patientId + " not found in Patient Service");
		}
	}

	@Override
	public String saveHistory(MedicalHistory dto) {
		logger.info("Saving medical history for patient ID: {}", dto.getPatientId());

		try {
			// Validate patient ID
			Patient patient = patientClient.getPatientById(dto.getPatientId());
		} catch (FeignException.NotFound ex) {
			logger.error("Invalid Patient ID: {}", dto.getPatientId());
			throw new PatientNotFoundException("Invalid Patient ID: " + dto.getPatientId());
		}

		MedicalHistory history = new MedicalHistory();
		history.setPatientId(dto.getPatientId());
		history.setDiagnosis(dto.getDiagnosis());
		history.setTreatment(dto.getTreatment());
		history.setDateOfVisit(dto.getDateOfVisit());

		repository.save(history);
		logger.info("Medical history saved successfully for patient ID: {}", dto.getPatientId());
		return "Medical history saved successfully.";
	}

	@Override
	public String updateHistory(Long id, MedicalHistory updated) {
		logger.info("Updating medical history for ID: {}", id);

		MedicalHistory existing = repository.findById(id).orElseThrow(() -> {
			logger.error("Medical history not found for ID: {}", id);
			return new HistoryNotFoundException("History with ID " + id + " not found.");
		});

		existing.setPatientId(updated.getPatientId());
		existing.setDiagnosis(updated.getDiagnosis());
		existing.setTreatment(updated.getTreatment());
		existing.setDateOfVisit(updated.getDateOfVisit());

		repository.save(existing);
		logger.info("Medical history updated successfully for ID: {}", id);
		return "Medical history updated successfully.";
	}

	@Override
	public MedicalHistory getById(Long id) {
		logger.info("Fetching medical history for ID: {}", id);

		return repository.findById(id).orElseThrow(() -> {
			logger.error("Medical history not found for ID: {}", id);
			return new HistoryNotFoundException("History with ID " + id + " not found.");
		});
	}

	@Override
	public List<MedicalHistory> getByPatientId(Long patientId) {
		logger.info("Fetching medical history for patient ID: {}", patientId);

		List<MedicalHistory> list = repository.findByPatientId(patientId);
		if (list.isEmpty()) {
			logger.warn("No medical history found for patient ID: {}", patientId);
			throw new HistoryNotFoundException("No history found for Patient ID " + patientId);
		}

		return list;
	}

	@Override
	public List<MedicalHistory> getAll() {
		logger.info("Fetching all medical history records.");
		return repository.findAll();
	}

	@Override
	public String deleteById(Long id) {
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
