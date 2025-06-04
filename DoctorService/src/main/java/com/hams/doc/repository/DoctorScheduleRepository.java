package com.hams.doc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hams.doc.model.DoctorSchedule;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

	List<DoctorSchedule> findBySpecialization(String specialization);

	List<DoctorSchedule> findByGenderIgnoreCase(String gender);
}
