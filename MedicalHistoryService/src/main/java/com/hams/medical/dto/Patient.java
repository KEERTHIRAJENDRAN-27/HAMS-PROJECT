package com.hams.medical.dto;

public class Patient {

	private Long patientId;
	private String name;
	private int age;
	private String bloodGroup;
	private String gender;
	private String dateOfBirth;
	private String medicalHistory;
	private String contactDetails;

	public Patient() {
		// TODO Auto-generated constructor stub
	}

	public Patient(Long patientId, String name, int age, String bloodGroup, String gender, String dateOfBirth,
			String medicalHistory, String contactDetails) {
		super();
		this.patientId = patientId;
		this.name = name;
		this.age = age;
		this.bloodGroup = bloodGroup;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.medicalHistory = medicalHistory;
		this.contactDetails = contactDetails;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

}
