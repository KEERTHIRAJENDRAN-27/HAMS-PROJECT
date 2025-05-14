package com.hams.medical.service;

import java.util.List;

import com.hams.medical.dto.MedicalHistoryPatientResponseDTO;
import com.hams.medical.exception.HistoryNotFoundException;
import com.hams.medical.exception.PatientNotFoundException;
import com.hams.medical.model.MedicalHistory;

public interface MedicalHistoryService {
	String saveHistory(MedicalHistory history) throws PatientNotFoundException;

	String updateHistory(Long id, MedicalHistory history) throws HistoryNotFoundException;

	MedicalHistory getById(Long id) throws HistoryNotFoundException;

	List<MedicalHistory> getByPatientId(Long patientId) throws HistoryNotFoundException;

	List<MedicalHistory> getAll();

	String deleteById(Long id) throws HistoryNotFoundException;

	MedicalHistoryPatientResponseDTO getMedicalHistoryWithPatient(Long patientId) throws PatientNotFoundException;
}
