package com.hams.appointment.feignclient;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hams.appointment.dto.DoctorScheduleDTO;

@FeignClient(value = "DOCTORSCHEDULESERVICE", url = "http://localhost:8003/doctor")
public interface DoctorClient {

	@GetMapping("/fetch/{id}")
	public DoctorScheduleDTO getDoctorScheduleById(@PathVariable Long id);

	@GetMapping("/fetchAll")
	public List<DoctorScheduleDTO> getAllDoctors();
	
	@GetMapping("/availableTimeSlots/{doctorId}")
	public List<LocalDateTime> getAvailableTimeSlots(@PathVariable Long doctorId);

}