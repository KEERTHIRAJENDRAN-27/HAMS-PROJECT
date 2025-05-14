package com.hams.appointment.dto;

public class AppointmentPatientResponseDTO {
	private AppointmentDTO appointment;
	private PatientProfile patient;
	private DoctorScheduleToAppointmentDTO doctor;

	public AppointmentPatientResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	public AppointmentPatientResponseDTO(AppointmentDTO appointment, PatientProfile patient,
			DoctorScheduleToAppointmentDTO doctor) {
		super();
		this.appointment = appointment;
		this.patient = patient;
		this.doctor = doctor;
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

	public DoctorScheduleToAppointmentDTO getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorScheduleToAppointmentDTO doctor) {
		this.doctor = doctor;
	}

}
