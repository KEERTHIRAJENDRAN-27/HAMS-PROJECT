package com.hams.appointment.dto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorScheduleDTO {

	private Long doctorId;
	private String doctorName;
	private String specialization;

	private String availableTimeSlots; // CSV string like "2025-05-11T10:00,2025-05-11T11:00"

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

	// Convert CSV string to List<LocalDateTime>
	public List<LocalDateTime> getAvailableTimeSlotList() {
		return Arrays.stream(availableTimeSlots.split(",")).map(LocalDateTime::parse).collect(Collectors.toList());
	}
}