package com.hams.appointment.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DoctorScheduleToAppointmentDTO {
	 private Long doctorId;
	    private String doctorName;
	    private String specialization;
	    private List<String> availableDays;
	    private String availableTime;
	    private List<String> bookedAppointments; // should be ISO date-time strings
	 
	    public DoctorScheduleToAppointmentDTO() {
			// TODO Auto-generated constructor stub
		}
	    
	    public DoctorScheduleToAppointmentDTO(Long doctorId, String doctorName, String specialization,
				List<String> availableDays, String availableTime, List<String> bookedAppointments) {
			super();
			this.doctorId = doctorId;
			this.doctorName = doctorName;
			this.specialization = specialization;
			this.availableDays = availableDays;
			this.availableTime = availableTime;
			this.bookedAppointments = bookedAppointments;
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
	 
	    public List<String> getBookedAppointments() {
	        return bookedAppointments;
	    }
	 
	    public void setBookedAppointments(List<String> bookedAppointments) {
	        this.bookedAppointments = bookedAppointments;
	    }

}
