package com.hams.doctor.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DoctorScheduleToAppointmentDTO {
	private Long doctorId;
	private String doctorName;
	private List<LocalDateTime> availableTimeSlots;

	public DoctorScheduleToAppointmentDTO() {
		// TODO Auto-generated constructor stub
	}

	public DoctorScheduleToAppointmentDTO(Long doctorId, String doctorName, List<LocalDateTime> availableTimeSlots) {
		super();
		this.doctorId = doctorId;
		this.doctorName = doctorName;
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

	public List<LocalDateTime> getAvailableTimeSlots() {
		return availableTimeSlots;
	}

	public void setAvailableTimeSlots(List<LocalDateTime> availableTimeSlots) {
		this.availableTimeSlots = availableTimeSlots;
	}
}
