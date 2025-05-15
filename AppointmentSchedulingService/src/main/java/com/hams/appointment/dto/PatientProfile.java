package com.hams.appointment.dto;

public class PatientProfile {

	private long patientId;
	private String name;
	private String gender;
	private int age;
	private String contact;
	private String email;

	public PatientProfile() {
		// TODO Auto-generated constructor stub
	}

	public PatientProfile(long patientId, String name, String gender, int age, String contact) {
		super();
		this.patientId = patientId;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.contact = contact;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
