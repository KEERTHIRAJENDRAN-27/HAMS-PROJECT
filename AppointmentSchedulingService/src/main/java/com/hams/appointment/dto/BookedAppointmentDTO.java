package com.hams.appointment.dto;

import java.time.LocalDateTime;

public class BookedAppointmentDTO {

	private Long appointmentId;
	private Long patientId;
	private String patientName;
	private LocalDateTime appointmentDate;

	public BookedAppointmentDTO() {
// Default constructor
	}

	public BookedAppointmentDTO(Long appointmentId, Long patientId, String patientName, LocalDateTime appointmentDate) {
		this.appointmentId = appointmentId;
		this.patientId = patientId;
		this.patientName = patientName;
		this.appointmentDate = appointmentDate;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public LocalDateTime getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDateTime appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
}