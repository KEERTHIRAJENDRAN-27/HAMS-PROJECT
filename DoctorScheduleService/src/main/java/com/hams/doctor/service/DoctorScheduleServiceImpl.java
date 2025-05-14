package com.hams.doctor.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hams.doctor.dto.BookedAppointmentDTO;
import com.hams.doctor.dto.DoctorScheduleDTO;
import com.hams.doctor.exception.DoctorAlreadyExistsException;
import com.hams.doctor.exception.DoctorNotFoundException;
import com.hams.doctor.exception.InvalidDayException;
import com.hams.doctor.exception.ScheduleNotFoundException;
import com.hams.doctor.feignclient.AppointmentClient;
import com.hams.doctor.model.DoctorSchedule;
import com.hams.doctor.repository.DoctorScheduleRepository;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

	@Autowired
	private DoctorScheduleRepository repository;

	@Autowired
	private AppointmentClient appointmentClient;

	private static final List<String> VALID_DAYS = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
			"Saturday", "Sunday");

	private void validateAvailableDays(List<String> days) {
		for (String day : days) {
			if (!VALID_DAYS.contains(day)) {
				throw new InvalidDayException("Invalid day: " + day + ". Use full weekday names (e.g., Monday).");
			}
		}
	}

	@Override
	public String addSchedule(DoctorScheduleDTO dto) {
		Optional<DoctorSchedule> existing = repository.findByDoctorId(dto.getDoctorId());
		if (existing.isPresent()) {
			throw new DoctorAlreadyExistsException("Schedule for doctor ID " + dto.getDoctorId() + " already exists");
		}

		validateAvailableDays(dto.getAvailableDays());

		DoctorSchedule schedule = new DoctorSchedule();
		schedule.setDoctorId(dto.getDoctorId());
		schedule.setDoctorName(dto.getDoctorName());
		schedule.setSpecialization(dto.getSpecialization());
		schedule.setAvailableDays(dto.getAvailableDays());
		schedule.setAvailableTime(dto.getAvailableTime());

		repository.save(schedule);
		return "Doctor schedule added successfully";
	}

	@Override
	public String updateSchedule(Long doctorId, DoctorScheduleDTO dto) throws ScheduleNotFoundException{
		DoctorSchedule existingSchedule = repository.findByDoctorId(doctorId)
				.orElseThrow(() -> new ScheduleNotFoundException("Schedule not found for doctor ID: " + doctorId));

		validateAvailableDays(dto.getAvailableDays());

		existingSchedule.setDoctorName(dto.getDoctorName());
		existingSchedule.setSpecialization(dto.getSpecialization());
		existingSchedule.setAvailableDays(dto.getAvailableDays());
		existingSchedule.setAvailableTime(dto.getAvailableTime());

		repository.save(existingSchedule);
		return "Doctor schedule updated successfully for doctor ID: " + doctorId;
	}

	@Override
	public DoctorSchedule getById(Long doctorId) {
		return repository.findById(doctorId)
				.orElseThrow(() -> new ScheduleNotFoundException("Schedule not found for ID: " + doctorId));
	}

	@Override
	public List<DoctorSchedule> getDoctorId() {
		return repository.findAll();
	}

	@Override
	public String deleteById(Long doctorId) {
		repository.findById(doctorId).orElseThrow(() -> new ScheduleNotFoundException("Schedule not found for ID: " + doctorId));
		repository.deleteById(doctorId);
		return "Doctor schedule deleted successfully";
	}

	@Override
	public boolean isDoctorAvailable(Long doctorId, LocalDateTime requestedTime) {
		DoctorSchedule schedule = repository.findByDoctorId(doctorId)
				.orElseThrow(() -> new ScheduleNotFoundException("Schedule not found for doctor ID: " + doctorId));

		String requestedDay = requestedTime.getDayOfWeek().toString(); // MONDAY, TUESDAY...
		if (!schedule.getAvailableDays()
				.contains(requestedDay.substring(0, 1).toUpperCase() + requestedDay.substring(1).toLowerCase())) {
			return false;
		}

		String[] times = schedule.getAvailableTime().split(" - ");
		LocalTime start = LocalTime.parse(times[0]);
		LocalTime end = LocalTime.parse(times[1]);
		LocalTime requested = requestedTime.toLocalTime();

		return !requested.isBefore(start) && !requested.isAfter(end);
	}

	@Override
	public List<BookedAppointmentDTO> getDoctorBookedAppointments(Long doctorId) {
		return appointmentClient.getBookedAppointmentsByDoctorId(doctorId);
	}

	@Override
	public DoctorSchedule getScheduleByDoctorId(Long doctorId) {
		return repository.findByDoctorId(doctorId)
				.orElseThrow(() -> new DoctorNotFoundException("Doctor ID " + doctorId + " not found"));
	}

	@Override
	public List<DoctorSchedule> getAll() {
		return repository.findAll();
	}
}