package com.hams.doctor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hams.doctor.model.DoctorSchedule;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {
	Optional<DoctorSchedule> findByDoctorId(Long doctorId);
}
