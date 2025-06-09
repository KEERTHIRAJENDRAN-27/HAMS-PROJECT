package com.hams.patient.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patient")
public class Patient {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long patientId;

	@NotBlank(message = "Patient name is required")
	@Size(max = 50, message = "Patient name should not exceed 50 characters")
	private String name;

	@NotNull(message = "Age cannot be null")
	@Positive(message = "Age must be positive")
	private int age;

	@NotBlank(message = "Blood group is required")
	@Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood group format")
	private String bloodGroup;

	@NotBlank(message = "Gender is required")
	@Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
	private String gender;

	@Past(message = "Date of Birth must be in the past")
	@NotNull(message = "Date of birth is required")
	private LocalDate dateOfBirth;

	@Size(max = 500, message = "Medical history should not exceed 500 characters")
	private String medicalHistory;
	@Email(message = "Invalid email format")
	private String email;

	@Pattern(regexp = "^\\d{10}$", message = "Contact number must be a 10-digit number")
	private String contactDetails;

	public Patient() {
		// TODO Auto-generated constructor stub
	}

	public Patient(Long patientId,
			@NotBlank(message = "Patient name is required") @Size(max = 50, message = "Patient name should not exceed 50 characters") String name,
			@NotNull(message = "Age cannot be null") @Positive(message = "Age must be positive") int age,
			@NotBlank(message = "Blood group is required") @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood group format") String bloodGroup,
			@NotBlank(message = "Gender is required") @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other") String gender,
			@Past(message = "Date of Birth must be in the past") @NotNull(message = "Date of birth is required") LocalDate dateOfBirth,
			@Size(max = 500, message = "Medical history should not exceed 500 characters") String medicalHistory,
			@Email(message = "Invalid email format") String email,
			@Pattern(regexp = "^\\d{10}$", message = "Contact number must be a 10-digit number") String contactDetails) {
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
