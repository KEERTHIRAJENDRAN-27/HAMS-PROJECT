package com.hams.doctor.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hams.doctor.dto.BookedAppointmentDTO;

@FeignClient(name = "APPOINTMENTSCHEDULINGSERVICE", path = "/appointment")
public interface AppointmentClient {
	@GetMapping("/booked/doctor/{doctorId}")
	List<BookedAppointmentDTO> getBookedAppointmentsByDoctorId(@PathVariable("doctorId") Long doctorId);

	@GetMapping("/fetchByDoctorId/{doctorId}")
	public List<BookedAppointmentDTO> getAppointmentsByDoctorId(@PathVariable long doctorId);
}
