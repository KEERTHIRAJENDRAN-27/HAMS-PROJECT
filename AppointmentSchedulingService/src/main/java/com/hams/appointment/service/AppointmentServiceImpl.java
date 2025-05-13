package com.hams.appointment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hams.appointment.dto.AppointmentDTO;
import com.hams.appointment.dto.DoctorScheduleDTO;
import com.hams.appointment.exception.AppointmentNotFoundException;
import com.hams.appointment.exception.InvalidDoctorException;
import com.hams.appointment.exception.InvalidPatientException;
import com.hams.appointment.feignclient.DoctorClient;
import com.hams.appointment.feignclient.PatientClient;
import com.hams.appointment.model.Appointment;
import com.hams.appointment.repository.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	@Autowired
	private AppointmentRepository repository;

	@Autowired
	private PatientClient patientClient;

	@Autowired
	private DoctorClient doctorClient;

	@Override
	public Appointment bookAppointment(AppointmentDTO dto) {
		logger.info("Booking appointment for Patient ID: {}, Doctor ID: {}", dto.getPatientId(), dto.getDoctorId());

		// Validate patient
		try {
			patientClient.getPatientById(dto.getPatientId());
		} catch (Exception e) {
			logger.error("Invalid Patient ID: {}", dto.getPatientId(), e);
			throw new InvalidPatientException("Invalid Patient ID: " + dto.getPatientId());
		}

		// Validate doctor
		try {
			doctorClient.getDoctorScheduleById(dto.getDoctorId());
		} catch (Exception e) {
			List<DoctorScheduleDTO> doctors = doctorClient.getAllDoctors();
			logger.error("Invalid Doctor ID: {}. Available Doctor IDs: {}", dto.getDoctorId(),
					doctors.stream().map(DoctorScheduleDTO::getDoctorId).collect(Collectors.toList()), e);
			throw new InvalidDoctorException("Invalid Doctor ID: " + dto.getDoctorId());
		}

		// Check if requested appointmentDate is available in doctor's available slots
		List<java.time.LocalDateTime> availableSlots = doctorClient.getAvailableTimeSlots(dto.getDoctorId());
		if (!availableSlots.contains(dto.getAppointmentDate())) {
			logger.warn("Requested slot {} is not available for Doctor ID: {}", dto.getAppointmentDate(),
					dto.getDoctorId());
			throw new InvalidDoctorException("Requested slot is not available for the selected doctor.");
		}

		// Proceed with booking
		Appointment appointment = new Appointment();
		appointment.setPatientId(dto.getPatientId());
		appointment.setDoctorId(dto.getDoctorId());
		appointment.setAppointmentDate(dto.getAppointmentDate());
		appointment.setStatus(dto.getStatus());

		logger.info("Appointment booked successfully: {}", appointment);
		return repository.save(appointment);
	}

	@Override
	public Appointment updateAppointment(Long id, AppointmentDTO dto) {
		logger.info("Updating appointment with ID: {}", id);

		Appointment existing = repository.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with ID: " + id));

		try {
			patientClient.getPatientById(dto.getPatientId());
		} catch (Exception e) {
			logger.error("Invalid Patient ID: {}", dto.getPatientId(), e);
			throw new InvalidPatientException("Invalid Patient ID: " + dto.getPatientId());
		}

		try {
			doctorClient.getDoctorScheduleById(dto.getDoctorId());
		} catch (Exception e) {
			logger.error("Invalid Doctor ID: {}", dto.getDoctorId(), e);
			throw new InvalidDoctorException("Invalid Doctor ID: " + dto.getDoctorId());
		}

		existing.setPatientId(dto.getPatientId());
		existing.setDoctorId(dto.getDoctorId());
		existing.setAppointmentDate(dto.getAppointmentDate());
		existing.setStatus(dto.getStatus());

		logger.info("Appointment updated successfully: {}", existing);
		return repository.save(existing);
	}

	@Override
	public void cancelAppointment(Long id) {
		logger.info("Cancelling appointment with ID: {}", id);

		Appointment appointment = repository.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found: " + id));
		appointment.setStatus("Cancelled");

		logger.info("Appointment cancelled successfully: {}", appointment);
		repository.save(appointment);
	}

	@Override
	public Appointment getAppointmentById(Long id) {
		logger.info("Fetching appointment with ID: {}", id);
		return repository.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found: " + id));
	}
}
