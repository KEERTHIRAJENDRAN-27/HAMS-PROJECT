package com.hams.doc.dto;

import java.time.LocalDateTime;

public class BookedAppointmentDTO {

	private Long appointmentId;
	private Long doctorId;
	private String patientName;
	private LocalDateTime appointmentDateTime;

	public BookedAppointmentDTO() {
		// TODO Auto-generated constructor stub
	}

	public BookedAppointmentDTO(Long appointmentId, Long doctorId, String patientName,
			LocalDateTime appointmentDateTime) {
		super();
		this.appointmentId = appointmentId;
		this.doctorId = doctorId;
		this.patientName = patientName;
		this.appointmentDateTime = appointmentDateTime;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public LocalDateTime getAppointmentDateTime() {
		return appointmentDateTime;
	}

	public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
		this.appointmentDateTime = appointmentDateTime;
	}

}
