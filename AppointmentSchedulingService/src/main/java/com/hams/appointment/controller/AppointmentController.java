package com.hams.appointment.controller;

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
import com.hams.appointment.model.Appointment;
import com.hams.appointment.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService service;

	@PostMapping("/book")
	public Appointment book(@RequestBody AppointmentDTO dto) {
		return service.bookAppointment(dto);
	}

	@PutMapping("/update/{id}")
	public Appointment update(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
		return service.updateAppointment(id, dto);
	}

	@DeleteMapping("/cancel/{id}")
	public String cancel(@PathVariable Long id) {
		service.cancelAppointment(id);
		return "Appointment cancelled.";
	}

	@GetMapping("/fetch/{id}")
	public Appointment getById(@PathVariable Long id) {
		return service.getAppointmentById(id);
	}
}
