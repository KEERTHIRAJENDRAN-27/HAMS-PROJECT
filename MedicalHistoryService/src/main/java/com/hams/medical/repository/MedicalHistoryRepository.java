package com.hams.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hams.medical.model.MedicalHistory;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
	List<MedicalHistory> findByPatientId(Long patientId);
}
