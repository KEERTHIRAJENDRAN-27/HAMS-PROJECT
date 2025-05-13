package com.hams.patient.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "patient")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long patientId;

	private String name;
	private int age;
	private String bloodGroup;
	private String gender;
	private LocalDate dateOfBirth;
	private String medicalHistory;
	@Email(message = "Invalid email format")
	private String email;
	private String contactDetails;

	public Patient() {
		// TODO Auto-generated constructor stub
	}

	public Patient(Long patientId, String name, int age, String bloodGroup, String gender, LocalDate dateOfBirth,
			String medicalHistory, @Email(message = "Invalid email format") String email, String contactDetails) {
		super();
		this.patientId = patientId;
		this.name = name;
		this.age = age;
		this.bloodGroup = bloodGroup;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.medicalHistory = medicalHistory;
		this.email = email;
		this.contactDetails = contactDetails;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
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
