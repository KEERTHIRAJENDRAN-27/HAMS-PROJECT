package com.hams.doctor.service;

import java.time.LocalDateTime;
import java.util.List;

import com.hams.doctor.dto.BookedAppointmentDTO;
import com.hams.doctor.dto.DoctorScheduleDTO;
import com.hams.doctor.model.DoctorSchedule;

public interface DoctorScheduleService {
    String addSchedule(DoctorScheduleDTO dto);
    String updateSchedule(Long doctorId, DoctorScheduleDTO dto);
    DoctorSchedule getById(Long doctorId);
    String deleteById(Long doctorId);
    boolean isDoctorAvailable(Long doctorId, LocalDateTime dateTime);
    List<BookedAppointmentDTO> getDoctorBookedAppointments(Long doctorId);
    DoctorSchedule getScheduleByDoctorId(Long doctorId);
	List<DoctorSchedule> getDoctorId();
	List<DoctorSchedule> getAll();
}
