package com.hams.doctor.service;

import java.time.LocalDateTime;
import java.util.List;

import com.hams.doctor.dto.DoctorScheduleDTO;
import com.hams.doctor.model.DoctorSchedule;

public interface DoctorScheduleService {
	String addSchedule(DoctorScheduleDTO dto);

	String updateSchedule(Long id, DoctorScheduleDTO dto);

	DoctorSchedule getById(Long id);

	List<DoctorSchedule> getAll();

	String deleteById(Long id);

	List<LocalDateTime> getAvailableTimeSlotsByDoctorId(Long doctorId);
}
