package com.hams.doc.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hams.doc.dto.BookedAppointmentDTO;
import com.hams.doc.dto.DoctorScheduleDTO;
import com.hams.doc.exception.DoctorAlreadyExistsException;
import com.hams.doc.exception.DoctorNotFoundException;
import com.hams.doc.exception.InvalidDayException;
import com.hams.doc.exception.ScheduleNotFoundException;
import com.hams.doc.model.DoctorSchedule;
import com.hams.doc.repository.DoctorScheduleRepository;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

	@Autowired
	private DoctorScheduleRepository repository;

	@Override
	public String addSchedule(DoctorScheduleDTO dto) {
	    List<DoctorSchedule> existingDoctors = repository.findAll(); // Fetch all existing doctors
	    
	    boolean exists = existingDoctors.stream().anyMatch(doc ->
	        doc.getEmail().equalsIgnoreCase(dto.getEmail()) ||
	        doc.getContact().equals(dto.getContact()) // Check both email and contact number
	    );

	    if (exists) {
	        throw new DoctorAlreadyExistsException("Doctor with the same email or contact number already exists.");
	    }

	    DoctorSchedule schedule = mapToEntity(dto);
	    repository.save(schedule);
	    return "Doctor schedule added successfully.";
	}


	@Override
	public String updateSchedule(Long doctorId, DoctorScheduleDTO dto) {
		DoctorSchedule schedule = repository.findById(doctorId)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + doctorId));

		updateEntity(schedule, dto);
		repository.save(schedule);
		return "Doctor schedule updated successfully.";
	}

	@Override
	public DoctorSchedule getById(Long doctorId) {
		System.out.println("Doctor Id :"+doctorId);
		return repository.findById(doctorId)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + doctorId));
	}

	@Override
	public String deleteById(Long doctorId) {
		if (!repository.existsById(doctorId)) {
			throw new DoctorNotFoundException("Doctor not found with ID: " + doctorId);
		}
		repository.deleteById(doctorId);
		return "Doctor schedule deleted.";
	}

	@Override
	public List<DoctorSchedule> getAll() {
		return repository.findAll();
	}

	@Override
	public boolean isDoctorAvailableBySpecialization(String specialization, LocalDateTime dateTime) {
		List<DoctorSchedule> doctors = repository.findBySpecialization(specialization);
		if (doctors.isEmpty()) {
			throw new ScheduleNotFoundException("No schedule found for specialization: " + specialization);
		}

		String day = capitalize(dateTime.getDayOfWeek().name().toLowerCase());
		String time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

		return doctors.stream().anyMatch(doc -> {
			List<String> days = List.of(doc.getAvailableDays().split(","));
			List<String> times = List.of(doc.getAvailableTime().split(","));

			if (!days.contains(day)) {
				throw new InvalidDayException("Doctor is not available on " + day);
			}
			return times.contains(time);
		});
	}

	@Override
	public List<DoctorSchedule> getSchedulesBySpecialization(String specialization) {
		List<DoctorSchedule> schedules = repository.findBySpecialization(specialization);
		if (schedules.isEmpty()) {
			throw new ScheduleNotFoundException("No schedules found for specialization: " + specialization);
		}
		return schedules;
	}

	@Override
	public List<BookedAppointmentDTO> getDoctorBookedAppointments(Long doctorId) {
		// Placeholder: Replace with actual appointment service logic
		return new ArrayList<>();
	}

	// Helper methods
	private DoctorSchedule mapToEntity(DoctorScheduleDTO dto) {
		DoctorSchedule entity = new DoctorSchedule();
		updateEntity(entity, dto);
		return entity;
	}

	private void updateEntity(DoctorSchedule entity, DoctorScheduleDTO dto) {
		entity.setDoctorId(dto.getDoctorId());
		entity.setDoctorName(dto.getDoctorName());
		entity.setSpecialization(dto.getSpecialization());
		entity.setGender(dto.getGender());
		entity.setAvailableDays(String.join(",", dto.getAvailableDays()));
		entity.setAvailableTime(String.join(",", dto.getAvailableTime()));
		entity.setEmail(dto.getEmail());
		entity.setContact(dto.getContact());
		entity.setExperience(dto.getExperience());
	}

	private String capitalize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	@Override
	public List<DoctorSchedule> getDoctorsByGender(String gender) throws DoctorNotFoundException {
		List<DoctorSchedule> doctors = repository.findByGenderIgnoreCase(gender);
		if (doctors.isEmpty()) {
			throw new DoctorNotFoundException("No doctors found with gender: " + gender);
		}
		return doctors;
	}
}