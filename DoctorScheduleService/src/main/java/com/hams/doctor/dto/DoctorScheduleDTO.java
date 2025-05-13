package com.hams.doctor.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DoctorScheduleDTO {
	private Long doctorId;
	private String doctorName;
	private String specialization;
	private List<LocalDateTime> availableTimeSlots;

	public DoctorScheduleDTO() {
		// TODO Auto-generated constructor stub
	}

	public DoctorScheduleDTO(Long doctorId, String doctorName, String specialization,
			List<LocalDateTime> availableTimeSlots) {
		super();
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.specialization = specialization;
		this.availableTimeSlots = availableTimeSlots;
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

	public List<LocalDateTime> getAvailableTimeSlots() {
		return availableTimeSlots;
	}

	public void setAvailableTimeSlots(List<LocalDateTime> availableTimeSlots) {
		this.availableTimeSlots = availableTimeSlots;
	}

}
