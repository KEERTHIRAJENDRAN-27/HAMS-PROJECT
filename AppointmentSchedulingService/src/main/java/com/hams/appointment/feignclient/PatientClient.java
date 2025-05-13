package com.hams.appointment.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hams.appointment.dto.PatientDTO;

@FeignClient(value = "PATIENTPROFILESERVICE", url = "http://localhost:8001/patients")
public interface PatientClient {

	@GetMapping("/fetchById/{id}")
	public PatientDTO getPatientById(@PathVariable Long id);

}
