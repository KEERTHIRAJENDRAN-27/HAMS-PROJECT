package com.hams.appointment.dto;

public class DoctorScheduleToAppointmentDTO {
	private Long doctorId;
	private String doctorName;

	private String specialization;
	private String gender;
	private String availableDays;

	private String availableTime;
	private String email;
	private String contact;
	private int experience;

	public String getAvailableDays() {
		return availableDays;
	}

	public String getAvailableTime() {
		return availableTime;
	}



	public DoctorScheduleToAppointmentDTO(Long doctorId, String doctorName, String specialization, String gender,
			String availableDays, String availableTime, String email, String contact, int experience) {
		super();
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.specialization = specialization;
		this.gender = gender;
		this.availableDays = availableDays;
		this.availableTime = availableTime;
		this.email = email;
		this.contact = contact;
		this.experience = experience;
	}

	public DoctorScheduleToAppointmentDTO() {
		// TODO Auto-generated constructor stub
	}

	public void setAvailableDays(String availableDays) {
		this.availableDays = availableDays;
	}

	public void setAvailableTime(String availableTime) {
		this.availableTime = availableTime;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

}
