package com.hams.appointment.dto;

public class AppointmentPatientRequestDTO {
	private AppointmentDTO appointment;
	private PatientProfile patient;

	public AppointmentPatientRequestDTO() {
		// TODO Auto-generated constructor stub
	}

	public AppointmentPatientRequestDTO(AppointmentDTO appointment, PatientProfile patient) {
		super();
		this.appointment = appointment;
		this.patient = patient;
	}

	public AppointmentDTO getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentDTO appointment) {
		this.appointment = appointment;
	}

	public PatientProfile getPatient() {
		return patient;
	}

	public void setPatient(PatientProfile patient) {
		this.patient = patient;
	}

}
