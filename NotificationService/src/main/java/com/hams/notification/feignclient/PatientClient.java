package com.hams.notification.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hams.notification.dto.PatientProfile;

@FeignClient(value = "PATIENTPROFILESERVICE", url = "http://localhost:8001/patients")
public interface PatientClient {
	@GetMapping("/fetchById/{id}")
	public PatientProfile getPatientById(@PathVariable Long id);
	@GetMapping("/getEmailById/{id}")
	public String getEmailById(@PathVariable Long id);
}
