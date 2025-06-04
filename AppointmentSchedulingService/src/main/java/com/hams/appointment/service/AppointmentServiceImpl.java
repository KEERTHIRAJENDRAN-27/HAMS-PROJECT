package com.hams.appointment.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hams.appointment.dto.AppointmentDTO;
import com.hams.appointment.dto.AppointmentPatientRequestDTO;
import com.hams.appointment.dto.AppointmentPatientResponseDTO;
import com.hams.appointment.dto.DoctorScheduleToAppointmentDTO;
import com.hams.appointment.dto.NotificationDTO;
import com.hams.appointment.dto.PatientProfile;
import com.hams.appointment.exception.AppointmentNotFoundException;
import com.hams.appointment.exception.DoctorNotAvailableException;
import com.hams.appointment.exception.InvalidDoctorException;
import com.hams.appointment.exception.InvalidPatientException;
import com.hams.appointment.feignclient.DoctorClient;
import com.hams.appointment.feignclient.NotificationClient;
import com.hams.appointment.feignclient.PatientClient;
import com.hams.appointment.model.Appointment;
import com.hams.appointment.repository.AppointmentRepository;

import jakarta.transaction.Transactional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository repo;

	@Autowired
	private PatientClient patientClient;

	@Autowired
	private DoctorClient doctorClient;

	@Autowired
	private NotificationClient notificationClient;

	@Override
	public String saveAppointment(AppointmentPatientRequestDTO requestDTO) {
		AppointmentDTO dto = requestDTO.getAppointment();

		// 1. Validate Patient ID
		PatientProfile patient;
		try {
			patient = patientClient.getPatientById(dto.getPatientId());
		} catch (Exception e) {
			throw new InvalidPatientException("Invalid Patient ID: " + dto.getPatientId());
		}

		// 2. Validate Doctor ID
		DoctorScheduleToAppointmentDTO doctor;
		try {
			doctor = doctorClient.getDoctorById(dto.getDoctorId());
			System.out.println(doctor);
		} catch (Exception e) {
			throw new InvalidDoctorException("Invalid Doctor ID: " + dto.getDoctorId() + " exception" + e);
		}
		
//		 3. Check if doctor is available on this day and time
	
		LocalDateTime appointmentDateTime = dto.getAppointmentDateTime();
		DayOfWeek appointmentDay = appointmentDateTime.getDayOfWeek();
		String dayName = appointmentDay.getDisplayName(TextStyle.FULL, Locale.ENGLISH); // e.g., "Tuesday"
		String timeString = appointmentDateTime.toLocalTime().toString().substring(0, 5); // e.g., "10:00"

		boolean isDayAvailable = doctor.getAvailableDays().contains(dayName);
		boolean isTimeAvailable = doctor.getAvailableTime().contains(timeString);

		if (!isDayAvailable || !isTimeAvailable) {
			throw new DoctorNotAvailableException("Doctor is not available on " + dayName + " at " + timeString);
		}

		// 4. Prevent double booking by checking existing appointments
		List<Appointment> existingAppointments = repo.findByDoctorIdAndAppointmentDateTime(dto.getDoctorId(),
				dto.getAppointmentDateTime());
		if (!existingAppointments.isEmpty()) {
			throw new RuntimeException("This time slot is already booked for the doctor.");
		}

		// 5. Prevent same patient from booking the same slot again
		List<Appointment> patientAppointments = repo.findByPatientIdAndAppointmentDateTime(dto.getPatientId(),
				dto.getAppointmentDateTime());
		if (!patientAppointments.isEmpty()) {
			throw new RuntimeException("This patient has already booked an appointment at the same time.");
		}

		// 6. Save appointment
		Appointment a = new Appointment();
		a.setPatientId(dto.getPatientId());
		a.setDoctorId(dto.getDoctorId());
		a.setAppointmentDateTime(dto.getAppointmentDateTime());
		a.setReason(dto.getReason());
		a.setStatus(dto.getStatus());
		repo.save(a);

		// 7. Send Notification
		NotificationDTO notification = new NotificationDTO();
		notification.setPatientId(patient.getPatientId());
		notification.setPatientEmail(patient.getEmail());
		notification.setMessage("Dear " + patient.getName() + ", your appointment with Doctor ID " + dto.getDoctorId()
				+ " is confirmed for " + dto.getAppointmentDateTime().toString());

		notificationClient.sendNotification(notification);

		return "Appointment Saved Successfully";
	}

	@Override
	public String updateAppointment(long id, AppointmentDTO dto) {
		// Retrieve the appointment by its ID
		Appointment a = repo.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Invalid ID"));
		// Update the appointment details
		a.setPatientId(dto.getPatientId());
		a.setDoctorId(dto.getDoctorId());
		a.setAppointmentDateTime(dto.getAppointmentDateTime());
		a.setReason(dto.getReason());
		a.setStatus(dto.getStatus());
		// Save the updated appointment
		repo.save(a);
		return "Appointment Updated";
	}

	@Override
	public List<AppointmentPatientResponseDTO> getAllAppointments() {
		// Retrieve all appointments
		List<Appointment> list = repo.findAll();
		List<AppointmentPatientResponseDTO> dtos = new ArrayList<>();
		for (Appointment a : list) {
			// Convert Appointment to AppointmentDTO
			AppointmentDTO dto = new AppointmentDTO();
			dto.setId(a.getId());
			dto.setPatientId(a.getPatientId());
			dto.setDoctorId(a.getDoctorId());
			dto.setAppointmentDateTime(a.getAppointmentDateTime());
			dto.setReason(a.getReason());
			dto.setStatus(a.getStatus());

			// Retrieve patient and doctor details
			PatientProfile p = patientClient.getPatientById(a.getPatientId());
			DoctorScheduleToAppointmentDTO d = doctorClient.getDoctorById(a.getDoctorId());

			// Combine appointment, patient, and doctor details into response DTO
			AppointmentPatientResponseDTO full = new AppointmentPatientResponseDTO();
			full.setAppointment(dto);
			full.setPatient(p);
			full.setDoctor(d);
			dtos.add(full);
		}
		return dtos;
	}

	@Override
	public AppointmentPatientResponseDTO getAppointmentById(long id) {
		// Retrieve the appointment by its ID
		Appointment a = repo.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Invalid ID"));
		// Convert Appointment to AppointmentDTO
		AppointmentDTO dto = new AppointmentDTO();
		dto.setId(a.getId());
		dto.setPatientId(a.getPatientId());
		dto.setDoctorId(a.getDoctorId());
		dto.setAppointmentDateTime(a.getAppointmentDateTime());
		dto.setReason(a.getReason());
		dto.setStatus(a.getStatus());

		// Retrieve patient and doctor details
		PatientProfile p = patientClient.getPatientById(a.getPatientId());
		DoctorScheduleToAppointmentDTO d = doctorClient.getDoctorById(a.getDoctorId());

		// Combine appointment, patient, and doctor details into response DTO
		AppointmentPatientResponseDTO full = new AppointmentPatientResponseDTO();
		full.setAppointment(dto);
		full.setPatient(p);
		full.setDoctor(d);
		return full;
	}

	@Override
	public List<AppointmentPatientResponseDTO> getAppointmentsByDoctorId(long doctorId) {
		// Retrieve appointments by doctor ID
		List<Appointment> list = repo.findByDoctorId(doctorId);
		if (list.isEmpty()) {
			throw new AppointmentNotFoundException("No appointments found for Doctor ID: " + doctorId);
		}
		List<AppointmentPatientResponseDTO> dtos = new ArrayList<>();
		for (Appointment a : list) {
			// Convert Appointment to AppointmentDTO
			AppointmentDTO dto = new AppointmentDTO();
			dto.setId(a.getId());
			dto.setPatientId(a.getPatientId());
			dto.setDoctorId(a.getDoctorId());
			dto.setAppointmentDateTime(a.getAppointmentDateTime());
			dto.setReason(a.getReason());
			dto.setStatus(a.getStatus());

			// Retrieve patient and doctor details
			PatientProfile p = patientClient.getPatientById(a.getPatientId());
			DoctorScheduleToAppointmentDTO d = doctorClient.getDoctorById(a.getDoctorId());

			// Combine appointment, patient, and doctor details into response DTO
			AppointmentPatientResponseDTO full = new AppointmentPatientResponseDTO();
			full.setAppointment(dto);
			full.setPatient(p);
			full.setDoctor(d);
			dtos.add(full);
		}
		return dtos;
	}

	@Transactional
	@Override
	public String deleteAppointment(long id) {
		// Check if the appointment exists before deleting
		if (!repo.existsById(id)) {
			throw new AppointmentNotFoundException("Appointment not found for ID: " + id);
		}
		repo.deleteById(id);
		return "Appointment Deleted Successfully";
	}

	@Override
	public List<DoctorScheduleToAppointmentDTO> getDoctorsBySpecialization(String specialization) {
		// Retrieve doctors by specialization using DoctorClient
		return doctorClient.getSchedulesBySpecialization(specialization);
	}
}
