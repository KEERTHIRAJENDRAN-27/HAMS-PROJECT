package com.hams.appointment.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long doctorId;

	@NotNull
	private Long patientId;

	@NotNull
	private LocalDateTime appointmentDateTime;

	@NotNull
	@Size(max = 255)
	private String status; // For example: 'booked', 'completed', 'canceled'

	private String reason;
	
	public Appointment() {
		// TODO Auto-generated constructor stub
	}
	
	public Appointment(Long id, @NotNull Long doctorId, @NotNull Long patientId,
			@NotNull LocalDateTime appointmentDateTime, @NotNull @Size(max = 255) String status, String reason) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.appointmentDateTime = appointmentDateTime;
		this.status = status;
		this.reason = reason;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
}
