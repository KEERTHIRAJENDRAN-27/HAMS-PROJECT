package com.hams.notification.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hams.notification.dto.MedicalHistoryResponseDTO;

@FeignClient(value = "MEDICALHISTORYSERVICE", url = "http://localhost:8004/medicalHistory")
public interface MedicalHistoryClient {
	@GetMapping("/get/{id}")
	public MedicalHistoryResponseDTO getById(@PathVariable Long id);
}