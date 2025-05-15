package com.hams.notification.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hams.notification.dto.AppointmentResponseDTO;

@FeignClient(value = "APPOINTMENTSCHEDULINGSERVICE", url = "http://localhost:8002/appointment")
public interface AppointmentClient {
	@GetMapping("/fetchById/{id}")
	public AppointmentResponseDTO getAppointmentById(@PathVariable Long appointmentId);
}