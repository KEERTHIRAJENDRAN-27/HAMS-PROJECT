package com.hams.medical.service;

import java.util.List;

import com.hams.medical.dto.MedicalHistoryPatientResponseDTO;
import com.hams.medical.model.MedicalHistory;

public interface MedicalHistoryService {
	String saveHistory(MedicalHistory history);

	String updateHistory(Long id, MedicalHistory history);

	MedicalHistory getById(Long id);

	List<MedicalHistory> getByPatientId(Long patientId);

	List<MedicalHistory> getAll();

	String deleteById(Long id);

	MedicalHistoryPatientResponseDTO getMedicalHistoryWithPatient(Long patientId);
}
