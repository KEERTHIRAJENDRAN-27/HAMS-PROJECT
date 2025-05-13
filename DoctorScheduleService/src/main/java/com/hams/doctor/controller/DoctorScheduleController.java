package com.hams.doctor.controller;

import java.time.LocalDateTime;
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

import com.hams.doctor.dto.DoctorScheduleDTO;
import com.hams.doctor.model.DoctorSchedule;
import com.hams.doctor.service.DoctorScheduleService;

@RestController
@RequestMapping("/doctor")
public class DoctorScheduleController {

	@Autowired
	private DoctorScheduleService service;

	@PostMapping("/add")
	public String add(@RequestBody DoctorScheduleDTO dto) {
		return service.addSchedule(dto);
	}

	@PutMapping("/update/{id}")
	public String update(@PathVariable Long id, @RequestBody DoctorScheduleDTO dto) {
		return service.updateSchedule(id, dto);
	}

	@GetMapping("/fetch/{id}")
	public DoctorSchedule get(@PathVariable Long id) {
		return service.getById(id);
	}

	@GetMapping("/fetchAll")
	public List<DoctorSchedule> getAll() {
		return service.getAll();
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		return service.deleteById(id);
	}

	@GetMapping("/availableTimeSlots/{doctorId}")
	public List<LocalDateTime> getAvailableTimeSlots(@PathVariable Long doctorId) {
		return service.getAvailableTimeSlotsByDoctorId(doctorId);
	}

}
