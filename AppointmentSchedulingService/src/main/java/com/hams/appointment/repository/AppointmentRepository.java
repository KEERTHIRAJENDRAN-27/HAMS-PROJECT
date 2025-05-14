package com.hams.appointment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hams.appointment.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	Optional<Appointment> findById(long id);

	void deleteById(long id);

	boolean existsById(long id);

	List<Appointment> findByDoctorId(long doctorId);
}
