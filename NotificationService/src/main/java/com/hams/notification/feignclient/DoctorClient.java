package com.hams.notification.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hams.notification.dto.DoctorResponseDTO;

@FeignClient(value = "DOCTORSCHEDULESERVICE", url = "http://localhost:8003/doctor")
public interface DoctorClient {

	@GetMapping("/fetch/{doctorId}")
	public DoctorResponseDTO getDoctorById(@PathVariable Long doctorId);

}