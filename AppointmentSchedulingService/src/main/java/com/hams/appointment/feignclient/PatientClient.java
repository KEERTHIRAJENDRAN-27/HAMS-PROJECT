package com.hams.appointment.feignclient;

import org.springframework.cloud.openfeign.FeignClient; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hams.appointment.dto.PatientProfile;

@FeignClient(name = "PATIENTPROFILESERVICE", path = "/patients")
public interface PatientClient {

	@GetMapping("/fetchById/{patientId}")
	public PatientProfile getPatientById(@PathVariable Long patientId);
}
