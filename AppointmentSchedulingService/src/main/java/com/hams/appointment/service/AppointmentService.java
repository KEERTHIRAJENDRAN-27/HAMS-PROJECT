package com.hams.appointment.service;

import com.hams.appointment.dto.AppointmentDTO;
import com.hams.appointment.model.Appointment;

public interface AppointmentService {
	Appointment bookAppointment(AppointmentDTO dto);

	Appointment updateAppointment(Long id, AppointmentDTO dto);

	void cancelAppointment(Long id);

	Appointment getAppointmentById(Long id);
}
