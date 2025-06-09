package com.hams.doc.controller;

import java.time.LocalDateTime;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.hams.doc.dto.BookedAppointmentDTO;
import com.hams.doc.dto.DoctorScheduleDTO;
import com.hams.doc.model.DoctorSchedule;
import com.hams.doc.service.DoctorScheduleService;

@RestController
@RequestMapping("/doctor")
//@CrossOrigin("*")
public class DoctorScheduleController {

	@Autowired
	private DoctorScheduleService service;

	@PostMapping("/add")
	public String add(@RequestBody DoctorScheduleDTO dto) {
		return service.addSchedule(dto);
	}

	@PutMapping("/update/{doctorId}")
	public String update(@PathVariable Long doctorId, @RequestBody DoctorScheduleDTO dto) {
		return service.updateSchedule(doctorId, dto);
	}

	@GetMapping("/fetch/{doctorId}")
	public DoctorSchedule getDoctorById(@PathVariable Long doctorId) {
		System.out.println("Doctor Id: "+doctorId);
		return service.getById(doctorId);
	}

	@GetMapping("/fetchAll")
	public List<DoctorSchedule> getAllDoctors() {
		return service.getAll();
	}

	@DeleteMapping("/delete/{doctorId}")
	public String delete(@PathVariable Long doctorId) {
		return service.deleteById(doctorId);
	}

	@GetMapping("/isAvailable/{specialization}/{dateTime}")
	public boolean isDoctorAvailableBySpecialization(@PathVariable String specialization,
			@PathVariable String dateTime) {
		LocalDateTime requested = LocalDateTime.parse(dateTime);
		return service.isDoctorAvailableBySpecialization(specialization, requested);
	}

	@GetMapping("/appointments/booked/{doctorId}")
	public List<BookedAppointmentDTO> getDoctorBookedAppointments(@PathVariable Long doctorId) {
		return service.getDoctorBookedAppointments(doctorId);
	}

	@GetMapping("/specialization/{specialization}")
	public List<DoctorSchedule> getSchedulesBySpecialization(@PathVariable String specialization) {
		return service.getSchedulesBySpecialization(specialization);
	}

	@GetMapping("/slots/{doctorId}")
	public Map<String, List<String>> getDoctorSlots(@PathVariable Long doctorId) {
		DoctorSchedule schedule = service.getById(doctorId);
		Map<String, List<String>> response = new HashMap<>();
		response.put("availableDays", List.of(schedule.getAvailableDays().split(",")));
		response.put("availableTime", List.of(schedule.getAvailableTime().split(",")));
		return response;
	}

	@GetMapping("/gender/{gender}")
	public List<DoctorSchedule> getDoctorsByGender(@PathVariable String gender) {
		return service.getDoctorsByGender(gender);
	}

}
