package com.hams.doctor.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor")
public class DoctorSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long doctorId;
	private String doctorName;
	private String specialization;

	@Column(length = 1000)
	private String availableTimeSlots; // stored as CSV string

	public List<LocalDateTime> getTimeSlotsAsList() {
		return Arrays.stream(availableTimeSlots.split(",")).map(LocalDateTime::parse).collect(Collectors.toList());
	}

	public void setTimeSlotsFromList(List<LocalDateTime> timeSlots) {
		this.availableTimeSlots = timeSlots.stream().map(LocalDateTime::toString).collect(Collectors.joining(","));
	}

	public DoctorSchedule() {
		// TODO Auto-generated constructor stub
	}

	public DoctorSchedule(Long id, Long doctorId, String doctorName, String specialization, String availableTimeSlots) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.specialization = specialization;
		this.availableTimeSlots = availableTimeSlots;
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

	public String getAvailableTimeSlots() {
		return availableTimeSlots;
	}

	public void setAvailableTimeSlots(String availableTimeSlots) {
		this.availableTimeSlots = availableTimeSlots;
	}

}
