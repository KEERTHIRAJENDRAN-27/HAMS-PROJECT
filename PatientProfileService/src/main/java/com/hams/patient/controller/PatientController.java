package com.hams.patient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/patients")
public class PatientController {

	@Autowired
	private PatientService service;

	@PostMapping("/register")
	public String registerPatient(@RequestBody Patient patient) {
		service.registerPatient(patient);
		return "Patient created";
	}

	@PutMapping("/update/{id}")
	public String updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
		service.updatePatient(id, patient);
		return "Patient updated";
	}

	@GetMapping("/fetchById/{id}")
	public Patient getPatientById(@PathVariable Long id) {
		return service.getPatientById(id);
	}

	@GetMapping("/fetchAll")
	public List<Patient> getAllPatients() {
		return service.getAllPatients();
	}

	@DeleteMapping("/delete/{id}")
	public String deletePatient(@PathVariable Long id) {
		return service.deletePatient(id);
	}
	
	@GetMapping("/getEmailById/{id}")
	public String getEmailById(@PathVariable Long id) {
	    Patient patient = service.getPatientById(id);
	    return patient.getEmail();
	}
}