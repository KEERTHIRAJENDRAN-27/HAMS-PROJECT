package com.hams.doc.service;

import java.time.LocalDateTime;
import java.util.List;

import com.hams.doc.dto.BookedAppointmentDTO;
import com.hams.doc.dto.DoctorScheduleDTO;
import com.hams.doc.exception.DoctorAlreadyExistsException;
import com.hams.doc.exception.DoctorNotFoundException;
import com.hams.doc.exception.InvalidDayException;
import com.hams.doc.exception.ScheduleNotFoundException;
import com.hams.doc.model.DoctorSchedule;

public interface DoctorScheduleService {

	String addSchedule(DoctorScheduleDTO dto) throws DoctorAlreadyExistsException;

	String updateSchedule(Long doctorId, DoctorScheduleDTO dto) throws DoctorNotFoundException;

	DoctorSchedule getById(Long doctorId) throws DoctorNotFoundException;

	String deleteById(Long doctorId) throws DoctorNotFoundException;

	List<DoctorSchedule> getAll();

	boolean isDoctorAvailableBySpecialization(String specialization, LocalDateTime dateTime)
			throws ScheduleNotFoundException, InvalidDayException;

	List<DoctorSchedule> getSchedulesBySpecialization(String specialization) throws ScheduleNotFoundException;

	List<BookedAppointmentDTO> getDoctorBookedAppointments(Long doctorId);

	List<DoctorSchedule> getDoctorsByGender(String gender) throws DoctorNotFoundException;

}
