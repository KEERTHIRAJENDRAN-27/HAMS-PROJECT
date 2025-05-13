package com.hams.doctor.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hams.doctor.dto.DoctorScheduleDTO;
import com.hams.doctor.exception.DoctorAlreadyExistsException;
import com.hams.doctor.exception.ScheduleNotFoundException;
import com.hams.doctor.model.DoctorSchedule;
import com.hams.doctor.repository.DoctorScheduleRepository;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

	private static final Logger logger = LoggerFactory.getLogger(DoctorScheduleServiceImpl.class);

	@Autowired
	private DoctorScheduleRepository repository;

	private String convertToCSV(List<LocalDateTime> timeSlots) {
		return timeSlots.stream().map(LocalDateTime::toString).collect(Collectors.joining(","));
	}

	@Override
	public String addSchedule(DoctorScheduleDTO dto) {
		logger.info("Attempting to add schedule for doctor ID: {}", dto.getDoctorId());

		Optional<DoctorSchedule> existingDoctor = repository.findByDoctorId(dto.getDoctorId());

		if (existingDoctor.isPresent()) {
			logger.warn("Doctor with ID {} already exists.", dto.getDoctorId());
			throw new DoctorAlreadyExistsException("Doctor with ID " + dto.getDoctorId() + " already exists.");
		}

		DoctorSchedule schedule = new DoctorSchedule();
		schedule.setDoctorId(dto.getDoctorId());
		schedule.setDoctorName(dto.getDoctorName());
		schedule.setSpecialization(dto.getSpecialization());
		schedule.setAvailableTimeSlots(convertToCSV(dto.getAvailableTimeSlots()));

		repository.save(schedule);
		logger.info("Doctor schedule added successfully for ID: {}", dto.getDoctorId());
		return "Doctor schedule registered successfully.";
	}

	@Override
	public String updateSchedule(Long id, DoctorScheduleDTO dto) {
		logger.info("Updating schedule for doctor ID: {}", id);

		DoctorSchedule schedule = repository.findByDoctorId(id).orElseThrow(() -> {
			logger.error("Schedule not found for ID: {}", id);
			return new ScheduleNotFoundException("Schedule not found with Doctor ID: " + id);
		});

		schedule.setDoctorId(dto.getDoctorId());
		schedule.setDoctorName(dto.getDoctorName());
		schedule.setSpecialization(dto.getSpecialization());
		schedule.setTimeSlotsFromList(dto.getAvailableTimeSlots());
		repository.save(schedule);

		logger.info("Doctor schedule updated successfully for ID: {}", id);
		return "Doctor schedule updated successfully.";
	}

	@Override
	public DoctorSchedule getById(Long id) {
		logger.info("Fetching schedule for doctor ID: {}", id);

		return repository.findById(id).orElseThrow(() -> {
			logger.error("Schedule not found for ID: {}", id);
			return new ScheduleNotFoundException("Schedule not found with ID: " + id);
		});
	}

	@Override
	public List<DoctorSchedule> getAll() {
		logger.info("Fetching all schedules.");
		return repository.findAll();
	}

	@Override
	public String deleteById(Long id) {
		logger.info("Attempting to delete schedule for doctor ID: {}", id);

		DoctorSchedule schedule = repository.findById(id).orElseThrow(() -> {
			logger.error("Schedule not found for ID: {}", id);
			return new ScheduleNotFoundException("Schedule not found with ID: " + id);
		});

		repository.delete(schedule);
		logger.info("Doctor schedule deleted successfully for ID: {}", id);
		return "Doctor schedule deleted successfully.";
	}

	@Override
	public List<LocalDateTime> getAvailableTimeSlotsByDoctorId(Long doctorId) {
		DoctorSchedule doctor = repository.findByDoctorId(doctorId)
				.orElseThrow(() -> new ScheduleNotFoundException("Doctor ID not found: " + doctorId));
		return doctor.getTimeSlotsAsList();
	}

}
