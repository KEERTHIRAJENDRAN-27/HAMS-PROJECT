package com.hams.appointment.service;

import java.util.List;


import com.hams.appointment.dto.AppointmentDTO;
import com.hams.appointment.dto.AppointmentPatientRequestDTO;
import com.hams.appointment.dto.AppointmentPatientResponseDTO;
import com.hams.appointment.dto.DoctorScheduleToAppointmentDTO;

public interface AppointmentService {
	String saveAppointment(AppointmentPatientRequestDTO requestDTO);

	String updateAppointment(long id, AppointmentDTO dto);

	List<AppointmentPatientResponseDTO> getAllAppointments();

	AppointmentPatientResponseDTO getAppointmentById(long id);
	
	List<AppointmentPatientResponseDTO> getAppointmentsByDoctorId(long doctorId);

	String deleteAppointment(long id);

	List<DoctorScheduleToAppointmentDTO> getDoctorsBySpecialization(String specialization);

	List<AppointmentPatientResponseDTO> getAppointmentsByPatientId(int patientId);
}
