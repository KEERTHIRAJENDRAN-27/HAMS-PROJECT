package com.hams.doc.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class DoctorScheduleDTO {

	@NotBlank(message = "Doctor name cannot be blank")
	private String doctorName;

	@NotBlank(message = "Specialization cannot be blank")
	private String specialization;

	@NotBlank(message = "Gender cannot be blank")
	@Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other")
	private String gender;

	@NotEmpty(message = "Available days cannot be empty")
	private List<@Pattern(regexp = "Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday", message = "Invalid day") String> availableDays;

	@NotEmpty(message = "Available time slots cannot be empty")
	private List<@Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "Time must be in HH:mm format") String> availableTime;

	@Email(message = "Invalid email format")
	@NotBlank(message = "Email cannot be blank")
	private String email;

	@NotBlank(message = "Contact number cannot be blank")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian contact number")
	private String contact;

	@Min(value = 0, message = "Experience must be non-negative")
	private int experience;

	public DoctorScheduleDTO() {
		// TODO Auto-generated constructor stub
	}

	public DoctorScheduleDTO(@NotBlank(message = "Doctor name cannot be blank") String doctorName,
			@NotBlank(message = "Specialization cannot be blank") String specialization,
			@NotBlank(message = "Gender cannot be blank") @Pattern(regexp = "Male|Female|Other", message = "Gender must be Male, Female, or Other") String gender,
			@NotEmpty(message = "Available days cannot be empty") List<@Pattern(regexp = "Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday", message = "Invalid day") String> availableDays,
			@NotEmpty(message = "Available time slots cannot be empty") List<@Pattern(regexp = "^([01]?\\d|2[0-3]):[0-5]\\d$", message = "Time must be in HH:mm format") String> availableTime,
			@Email(message = "Invalid email format") @NotBlank(message = "Email cannot be blank") String email,
			@NotBlank(message = "Contact number cannot be blank") @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian contact number") String contact,
			@Min(value = 0, message = "Experience must be non-negative") int experience) {
		super();
		this.doctorName = doctorName;
		this.specialization = specialization;
		this.gender = gender;
		this.availableDays = availableDays;
		this.availableTime = availableTime;
		this.email = email;
		this.contact = contact;
		this.experience = experience;
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

	public List<String> getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(List<String> availableDays) {
		this.availableDays = availableDays;
	}

	public List<String> getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(List<String> availableTime) {
		this.availableTime = availableTime;
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
