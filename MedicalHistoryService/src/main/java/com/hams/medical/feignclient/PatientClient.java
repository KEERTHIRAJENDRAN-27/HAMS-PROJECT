package com.hams.medical.feignclient;

import org.springframework.cloud.openfeign.FeignClient;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hams.medical.dto.Patient;

@FeignClient(value = "PATIENTPROFILESERVICE", url = "http://localhost:8001/patients")
public interface PatientClient {
	@GetMapping("/fetchById/{id}")
	public Patient getPatientById(@PathVariable Long id);
}
