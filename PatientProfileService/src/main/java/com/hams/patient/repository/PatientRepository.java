package com.hams.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hams.patient.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
