package com.hams.medical.dto;

import java.util.List;

import com.hams.medical.model.MedicalHistory;

public class MedicalHistoryPatientResponseDTO {
	 
    private Patient patient;
    private List<MedicalHistory> medicalHistory;
 
    public MedicalHistoryPatientResponseDTO() {}
 
    public MedicalHistoryPatientResponseDTO(Patient patient, List<MedicalHistory> medicalHistory) {
        this.patient = patient;
        this.medicalHistory = medicalHistory;
    }

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public List<MedicalHistory> getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(List<MedicalHistory> medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
    
}