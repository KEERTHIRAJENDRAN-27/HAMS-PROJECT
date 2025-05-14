package com.hams.doctor.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "doctor_schedule")
public class DoctorSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Doctor ID cannot be null")
	private Long doctorId;

	@NotNull(message = "Doctor Name cannot be null")
	private String doctorName;

	@NotNull(message = "Enter any specialization")
	private String specialization;

	@ElementCollection
	@CollectionTable(name = "doctor_available_days", joinColumns = @JoinColumn(name = "doctor_schedule_id"))
	@Column(name = "available_day")
	private List<String> availableDays;

	@Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d - ([01]\\d|2[0-3]):[0-5]\\d$", message = "Available time must be in 24-hour format HH:mm - HH:mm")
	private String availableTime;

	@ElementCollection
	@CollectionTable(name = "doctor_booked_appointments", joinColumns = @JoinColumn(name = "doctor_schedule_id"))
	@Column(name = "booked_appointment")
	private List<LocalDateTime> bookedAppointments;

	public DoctorSchedule() {
		// Default constructor
	}

	public DoctorSchedule(Long id, @NotNull(message = "Doctor ID cannot be null") Long doctorId,
			@NotNull(message = "Doctor Name cannot be null") String doctorName,
			@NotNull(message = "Enter any specialization") String specialization, List<String> availableDays,
			@Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d - ([01]\\d|2[0-3]):[0-5]\\d$", message = "Available time must be in 24-hour format HH:mm - HH:mm") String availableTime,
			List<LocalDateTime> bookedAppointments) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.specialization = specialization;
		this.availableDays = availableDays;
		this.availableTime = availableTime;
		this.bookedAppointments = bookedAppointments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<String> getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(List<String> availableDays) {
		this.availableDays = availableDays;
	}

	public String getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(String availableTime) {
		this.availableTime = availableTime;
	}

	public List<LocalDateTime> getBookedAppointments() {
		return bookedAppointments;
	}

	public void setBookedAppointments(List<LocalDateTime> bookedAppointments) {
		this.bookedAppointments = bookedAppointments;
	}
}