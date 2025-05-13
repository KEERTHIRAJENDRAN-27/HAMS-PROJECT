package com.hams.notification.dto;

public class PatientProfile {
	private Long patientId;
	private String name;
	private String email;
	private String contactDetails;

	public PatientProfile() {
		// TODO Auto-generated constructor stub
	}

	public PatientProfile(Long patientId, String name, String email, String contactDetails) {
		super();
		this.patientId = patientId;
		this.name = name;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

}
