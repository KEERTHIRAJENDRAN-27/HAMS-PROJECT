package com.hams.patient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hams.patient.model.Patient;
import com.hams.patient.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
//@CrossOrigin("*")
public class PatientController {

	@Autowired
	private PatientService service;

	@PostMapping("/register")
	public String registerPatient(@Valid @RequestBody Patient patient) {
		service.registerPatient(patient);
		return "Patient registered successfully";
	}

	@PutMapping("/update/{patientId}")
	public String updatePatient(@Valid @PathVariable Long patientId, @RequestBody Patient patient) {
		service.updatePatient(patientId, patient);
		return "Patient updated successfully.";
	}

	@GetMapping("/fetchById/{patientId}")
	public Patient getPatientById(@PathVariable Long patientId) {
		return service.getPatientById(patientId);
	}

	@GetMapping("/fetchAll")
	public List<Patient> getAllPatients() {
		return service.getAllPatients();
	}

	@DeleteMapping("/delete/{patientId}")
	public String deletePatient(@PathVariable Long patientId) {
		return service.deletePatient(patientId);
	}
	
	@GetMapping("/getEmailById/{patientId}")
	public String getEmailById(@PathVariable Long patientId) {
	    Patient patient = service.getPatientById(patientId);
	    return patient.getEmail();
	}
}