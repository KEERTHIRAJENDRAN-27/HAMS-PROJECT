package com.hams.appointment.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hams.appointment.dto.AppointmentDTO;
import com.hams.appointment.dto.AppointmentPatientRequestDTO;
import com.hams.appointment.dto.AppointmentPatientResponseDTO;
import com.hams.appointment.dto.DoctorScheduleToAppointmentDTO;
import com.hams.appointment.dto.PatientProfile;
import com.hams.appointment.exception.AppointmentNotFoundException;
import com.hams.appointment.exception.DoctorNotAvailableException;
import com.hams.appointment.exception.InvalidDoctorException;
import com.hams.appointment.exception.InvalidPatientException;
import com.hams.appointment.feignclient.DoctorClient;
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

	@Override
	public String saveAppointment(AppointmentPatientRequestDTO requestDTO) {
		AppointmentDTO dto = requestDTO.getAppointment();

		// 1. Validate Patient
		PatientProfile patient;
		try {
			patient = patientClient.getPatientById(dto.getPatientId());
		} catch (Exception e) {
			throw new InvalidPatientException("Invalid Patient ID: " + dto.getPatientId());
		}

		// 2. Validate Doctor
		DoctorScheduleToAppointmentDTO doctor;
		try {
			doctor = doctorClient.getDoctorById(dto.getDoctorId());
		} catch (Exception e) {
			throw new InvalidDoctorException("Invalid Doctor ID: " + dto.getDoctorId());
		}

		// 3. Check Doctor Availability
		LocalDateTime appointmentDateTime = dto.getAppointmentDateTime();
		DayOfWeek appointmentDay = appointmentDateTime.getDayOfWeek(); // MONDAY, TUESDAY
		String dayName = appointmentDay.name().charAt(0) + appointmentDay.name().substring(1).toLowerCase(); // "Monday"
		String timeString = appointmentDateTime.toLocalTime().toString().substring(0, 5); // "HH:mm"

		boolean isDayAvailable = doctor.getAvailableDays().contains(dayName);
		boolean isTimeAvailable = doctor.getAvailableTime().contains(timeString);

		if (!isDayAvailable || !isTimeAvailable) {
			throw new DoctorNotAvailableException("Doctor is not available on " + dayName + " at " + timeString);
		}

		// 4. Save Appointment
		Appointment a = new Appointment();
		a.setPatientId(dto.getPatientId());
		a.setDoctorId(dto.getDoctorId());
		a.setAppointmentDateTime(dto.getAppointmentDateTime());
		a.setReason(dto.getReason());
		a.setStatus(dto.getStatus());
		repo.save(a);
		return "Appointment Saved Successfully";
	}

	@Override
	public String updateAppointment(long id, AppointmentDTO dto) {
		Appointment a = repo.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Invalid ID"));
		a.setPatientId(dto.getPatientId());
		a.setDoctorId(dto.getDoctorId());
		a.setAppointmentDateTime(dto.getAppointmentDateTime());
		a.setReason(dto.getReason());
		a.setStatus(dto.getStatus());
		repo.save(a);
		return "Appointment Updated";
	}

	@Override
	public List<AppointmentPatientResponseDTO> getAllAppointments() {
		List<Appointment> list = repo.findAll();
		List<AppointmentPatientResponseDTO> dtos = new ArrayList<>();
		for (Appointment a : list) {
			AppointmentDTO dto = new AppointmentDTO();
			dto.setId(a.getId());
			dto.setPatientId(a.getPatientId());
			dto.setDoctorId(a.getDoctorId());
			dto.setAppointmentDateTime(a.getAppointmentDateTime());
			dto.setReason(a.getReason());
			dto.setStatus(a.getStatus());

			PatientProfile p = patientClient.getPatientById(a.getPatientId());
			DoctorScheduleToAppointmentDTO d = doctorClient.getDoctorById(a.getDoctorId());

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
		Appointment a = repo.findById(id).orElseThrow(() -> new AppointmentNotFoundException("Invalid ID"));
		AppointmentDTO dto = new AppointmentDTO();
		dto.setId(a.getId());
		dto.setPatientId(a.getPatientId());
		dto.setDoctorId(a.getDoctorId());
		dto.setAppointmentDateTime(a.getAppointmentDateTime());
		dto.setReason(a.getReason());
		dto.setStatus(a.getStatus());

		PatientProfile p = patientClient.getPatientById(a.getPatientId());
		DoctorScheduleToAppointmentDTO d = doctorClient.getDoctorById(a.getDoctorId());

		AppointmentPatientResponseDTO full = new AppointmentPatientResponseDTO();
		full.setAppointment(dto);
		full.setPatient(p);
		full.setDoctor(d);
		return full;
	}

	@Override
	public List<AppointmentPatientResponseDTO> getAppointmentsByDoctorId(long doctorId) {
		List<Appointment> list = repo.findByDoctorId(doctorId);
		if (list.isEmpty()) {
			throw new AppointmentNotFoundException("No appointments found for Doctor ID: " + doctorId);
		}
		List<AppointmentPatientResponseDTO> dtos = new ArrayList<>();
		for (Appointment a : list) {
			AppointmentDTO dto = new AppointmentDTO();
			dto.setId(a.getId());
			dto.setPatientId(a.getPatientId());
			dto.setDoctorId(a.getDoctorId());
			dto.setAppointmentDateTime(a.getAppointmentDateTime());
			dto.setReason(a.getReason());
			dto.setStatus(a.getStatus());

			PatientProfile p = patientClient.getPatientById(a.getPatientId());
			DoctorScheduleToAppointmentDTO d = doctorClient.getDoctorById(a.getDoctorId());

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
		if (!repo.existsById(id)) {
			throw new AppointmentNotFoundException("Appointment not found for ID: " + id);
		}
		repo.deleteById(id);
		// logger.info("Appointment deleted successfully for ID: {}", id);
		return "Appointment Deleted Successfully";
	}

	@Override
	public List<DoctorScheduleToAppointmentDTO> getDoctorsBySpecialization(String specialization) {
		return doctorClient.getDoctorsBySpecialization(specialization);
	}
}
