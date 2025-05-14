package com.hams.doctor.model;
import com.hams.doctor.dto.DoctorScheduleDTO;
import com.hams.doctor.model.DoctorSchedule;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
 
public class DoctorScheduleMapper {
 
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
 
    public static DoctorScheduleDTO toDTO(DoctorSchedule entity) {
        List<String> formattedAppointments = entity.getBookedAppointments().stream()
                .map(dateTime -> dateTime.format(formatter))
                .collect(Collectors.toList());
 
        return new DoctorScheduleDTO(
                entity.getDoctorId(),
                entity.getDoctorName(),
                entity.getSpecialization(),
                entity.getAvailableDays(),
                entity.getAvailableTime(),
                formattedAppointments
        );
    }
 
    public static DoctorSchedule toEntity(DoctorScheduleDTO dto) {
        List<LocalDateTime> appointmentDates = dto.getBookedAppointments().stream()
                .map(dateStr -> LocalDateTime.parse(dateStr, formatter))
                .collect(Collectors.toList());
 
        DoctorSchedule entity = new DoctorSchedule();
        entity.setDoctorId(dto.getDoctorId());
        entity.setDoctorName(dto.getDoctorName());
        entity.setSpecialization(dto.getSpecialization());
        entity.setAvailableDays(dto.getAvailableDays());
        entity.setAvailableTime(dto.getAvailableTime());
        entity.setBookedAppointments(appointmentDates);
        return entity;
    }
}