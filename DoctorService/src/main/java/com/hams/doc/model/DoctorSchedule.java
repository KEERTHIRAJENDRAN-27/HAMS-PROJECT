package com.hams.doc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "doctor_Info")
public class DoctorSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long doctorId;

	@NotBlank(message = "Doctor Name cannot be blank")
	private String doctorName;

	@NotBlank(message = "Specialization cannot be blank")
	private String specialization;

	@NotBlank(message = "Gender cannot be blank")
	@Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
	private String gender;

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email cannot be blank")
	private String email;

	@NotBlank(message = "Contact number cannot be blank")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian contact number")
	private String contact;

	@Min(value = 0, message = "Experience must be non-negative")
	private int experience;

	@NotBlank(message = "Available days cannot be blank")
	private String availableDays; // e.g., "Monday,Wednesday,Friday"

	@NotBlank(message = "Available time slots cannot be blank")
	private String availableTime; // e.g., "09:30,13:40,23:30"

	public DoctorSchedule() {
		// TODO Auto-generated constructor stub
	}

	public DoctorSchedule(Long doctorId, @NotBlank(message = "Doctor Name cannot be blank") String doctorName,
			@NotBlank(message = "Specialization cannot be blank") String specialization,
			@NotBlank(message = "Gender cannot be blank") @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other") String gender,
			@Email(message = "Invalid email format") @NotBlank(message = "Email cannot be blank") String email,
			@NotBlank(message = "Contact number cannot be blank") @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian contact number") String contact,
			@Min(value = 0, message = "Experience must be non-negative") int experience,
			@NotBlank(message = "Available days cannot be blank") String availableDays,
			@NotBlank(message = "Available time slots cannot be blank") String availableTime) {
		super();
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.specialization = specialization;
		this.gender = gender;
		this.email = email;
		this.contact = contact;
		this.experience = experience;
		this.availableDays = availableDays;
		this.availableTime = availableTime;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getDoctorName() {
		return doctorName;
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

	public String getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(String availableDays) {
		this.availableDays = availableDays;
	}

	public String getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(String availableTime) {
		this.availableTime = availableTime;
	}

}