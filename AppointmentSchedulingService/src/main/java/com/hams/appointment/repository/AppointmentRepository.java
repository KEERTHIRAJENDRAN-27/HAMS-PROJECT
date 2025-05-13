package com.hams.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hams.appointment.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
