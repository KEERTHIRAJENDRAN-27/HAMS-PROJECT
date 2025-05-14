package com.hams.appointment.dto;

import java.time.LocalDateTime;

public class AppointmentDTO {
	private Long id;
	private Long doctorId;
	private Long patientId;
	private LocalDateTime appointmentDateTime;
	private String reason;
	private String status;

	public AppointmentDTO() {
		// TODO Auto-generated constructor stub
	}

	public AppointmentDTO(Long id, Long doctorId, Long patientId, LocalDateTime appointmentDateTime, String reason,
			String status) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.appointmentDateTime = appointmentDateTime;
		this.reason = reason;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public LocalDateTime getAppointmentDateTime() {
		return appointmentDateTime;
	}

	public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
