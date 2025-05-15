package com.hams.appointment.controller;

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

import com.hams.appointment.dto.AppointmentDTO;
import com.hams.appointment.dto.AppointmentPatientRequestDTO;
import com.hams.appointment.dto.AppointmentPatientResponseDTO;
import com.hams.appointment.dto.DoctorScheduleToAppointmentDTO;
import com.hams.appointment.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService service;

	@PostMapping("/save")
	public String saveAppointment(@Valid @RequestBody AppointmentPatientRequestDTO requestDTO) {
		return service.saveAppointment(requestDTO);
	}

	@PutMapping("/update/{id}")
	public String updateAppointment(@PathVariable int id, @RequestBody AppointmentDTO dto) {
		return service.updateAppointment(id, dto);
	}

	@GetMapping("/fetchAll")
	public List<AppointmentPatientResponseDTO> getAllAppointments() {
		return service.getAllAppointments();
	}

	@GetMapping("/fetchById/{id}")
	public AppointmentPatientResponseDTO getAppointmentById(@PathVariable int id) {
		return service.getAppointmentById(id);
	}

	@GetMapping("/fetchByDoctorId/{doctorId}")
	public List<AppointmentPatientResponseDTO> getAppointmentsByDoctorId(@PathVariable long doctorId) {
		return service.getAppointmentsByDoctorId(doctorId);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteAppointment(@PathVariable int id) {
		return service.deleteAppointment(id);
	}

	@GetMapping("/doctors/fetchBySpecialization/{specialization}")
	public List<DoctorScheduleToAppointmentDTO> getDoctorsBySpecialization(@PathVariable String specialization) {
		return service.getDoctorsBySpecialization(specialization);
	}
}
