package com.hams.appointment.feignclient;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hams.appointment.dto.DoctorScheduleToAppointmentDTO;

@FeignClient(name = "DOCTORSERVICE", path = "/doctor")
public interface DoctorClient {

	@GetMapping("/fetch/{doctorId}")
	public DoctorScheduleToAppointmentDTO getDoctorById(@PathVariable Long doctorId);

	@GetMapping("/fetchAll")
	public List<DoctorScheduleToAppointmentDTO> getAllDoctors();

	@GetMapping("/specialization/{specialization}")
	public List<DoctorScheduleToAppointmentDTO> getSchedulesBySpecialization(@PathVariable String specialization);

	@GetMapping("/slots/{doctorId}")
	public Map<String, List<String>> getDoctorSlots(@PathVariable Long doctorId);
}
