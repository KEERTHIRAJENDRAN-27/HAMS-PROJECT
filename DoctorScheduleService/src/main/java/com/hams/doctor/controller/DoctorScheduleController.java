package com.hams.doctor.controller;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hams.doctor.dto.BookedAppointmentDTO;
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

	@PutMapping("/update/{doctorId}")
	public String update(@PathVariable Long doctorId, @RequestBody DoctorScheduleDTO dto) {
		return service.updateSchedule(doctorId, dto);
	}

	@GetMapping("/fetch/{doctorId}")
	public DoctorSchedule getDoctorById(@PathVariable Long doctorId) {
		return service.getById(doctorId);
	}

	@GetMapping("/fetchAll")
	public List<DoctorSchedule> getDoctorId() {
		return service.getAll();
	}

	@DeleteMapping("/delete/{doctorId}")
	public String delete(@PathVariable Long doctorId) {
		return service.deleteById(doctorId);
	}

	@GetMapping("/isAvailable/{doctorId}/{dateTime}")
	public boolean isDoctorAvailable(@PathVariable Long doctorId, @PathVariable String dateTime) {
		LocalDateTime requested = LocalDateTime.parse(dateTime);
		return service.isDoctorAvailable(doctorId, requested);
	}

	@GetMapping("/appointments/booked/{doctorId}")
	public List<BookedAppointmentDTO> getDoctorBookedAppointments(@PathVariable Long doctorId) {
		return service.getDoctorBookedAppointments(doctorId);
	}

	@GetMapping("/schedule/{id}/days")
	public List<String> getAvailableDays(@PathVariable Long id) {
		return service.getById(id).getAvailableDays();
	}

	@GetMapping("/schedule/{id}/appointments")
	public List<LocalDateTime> getBookedAppointments(@PathVariable Long id) {
		return service.getById(id).getBookedAppointments();
	}

	@GetMapping("/schedule/slots/{doctorId}")
	public Map<String, Object> getDoctorSlots(@PathVariable Long doctorId) {
		DoctorSchedule schedule = service.getScheduleByDoctorId(doctorId);
		Map<String, Object> response = new HashMap<>();
		response.put("availableDays", schedule.getAvailableDays());
		response.put("availableTime", schedule.getAvailableTime());
		response.put("bookedAppointments", schedule.getBookedAppointments());
		return response;
	}
}
