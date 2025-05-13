package com.hams.appointment.dto;

import java.time.LocalDateTime;

public class AppointmentDTO {
	private Long patientId;
	private Long doctorId;
	private LocalDateTime appointmentDate;
	private String status;

	public AppointmentDTO() {
		// TODO Auto-generated constructor stub
	}

	public AppointmentDTO(Long patientId, Long doctorId, LocalDateTime appointmentDate, String status) {
		super();
		this.patientId = patientId;
		this.doctorId = doctorId;
		this.appointmentDate = appointmentDate;
		this.status = status;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public LocalDateTime getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDateTime appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
