package com.hams.appointment.feignclient;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hams.appointment.dto.DoctorScheduleToAppointmentDTO;

@FeignClient(name = "DOCTORSCHEDULESERVICE", path = "/doctor")
public interface DoctorClient {

	@GetMapping("/doctor/fetchBySpecialization/{specialization}")
	List<DoctorScheduleToAppointmentDTO> getDoctorsBySpecialization(@PathVariable String specialization);

	@GetMapping("/fetch/{doctorId}")
	public DoctorScheduleToAppointmentDTO getDoctorById(@PathVariable Long doctorId);

	@GetMapping("/doctor/fetchAvailableTimeSlots/{doctorId}")
	public List<LocalDateTime> getDoctorAvailableTimeSlots(@PathVariable Long doctorId);
}
