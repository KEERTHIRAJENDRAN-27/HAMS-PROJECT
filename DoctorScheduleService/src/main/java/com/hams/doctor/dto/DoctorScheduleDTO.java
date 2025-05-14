package com.hams.doctor.dto;
 
import java.util.List;
 
public class DoctorScheduleDTO {
 
    private Long doctorId;
    private String doctorName;
    private String specialization;
    private List<String> availableDays;
    private String availableTime;
    private List<String> bookedAppointments; // should be ISO date-time strings
 
    public DoctorScheduleDTO() {
    }
 
    public DoctorScheduleDTO(Long doctorId, String doctorName, String specialization,
                             List<String> availableDays, String availableTime, List<String> bookedAppointments) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.availableDays = availableDays;
        this.availableTime = availableTime;
        this.bookedAppointments = bookedAppointments;
    }
 
    // Getters and setters
 
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