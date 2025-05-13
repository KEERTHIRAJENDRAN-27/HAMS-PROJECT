package com.hams.appointment.dto;

import com.hams.appointment.model.Appointment;

public class AppointmentResponseDTO {
	private Appointment appointment;

	public AppointmentResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	public AppointmentResponseDTO(Appointment appointment) {
		super();
		this.appointment = appointment;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

}
