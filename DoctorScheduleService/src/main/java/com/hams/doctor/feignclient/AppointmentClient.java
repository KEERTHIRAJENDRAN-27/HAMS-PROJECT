package com.hams.doctor.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hams.doctor.dto.DoctorScheduleToAppointmentDTO;

@FeignClient(name = "APPOINTMENTSCHEDULINGSERVICE", url = "http://localhost:8002/appointment")
public interface AppointmentClient {

    @PostMapping("/create")
    String createAppointment(@RequestBody DoctorScheduleToAppointmentDTO dto);

    @GetMapping("/fetchByDoctorId/{doctorId}")
    DoctorScheduleToAppointmentDTO getAppointmentByDoctorId(@PathVariable Long doctorId);

    @PutMapping("/update/{doctorId}")
    String updateAppointment(@PathVariable Long doctorId, @RequestBody DoctorScheduleToAppointmentDTO dto);

    @DeleteMapping("/deleteByDoctorId/{doctorId}")
    String deleteAppointmentByDoctorId(@PathVariable Long doctorId);

    @GetMapping("/all")
    List<DoctorScheduleToAppointmentDTO> getAllAppointments();
}
