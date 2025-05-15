package com.hams.notification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hams.notification.dto.NotificationDTO;
import com.hams.notification.service.NotificationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {

	private final NotificationService service;
	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@PostMapping("/send")
	public NotificationDTO send(@Valid @RequestBody NotificationDTO dto) {
		logger.info("Sending notification: {}", dto);
		return service.sendNotification(dto);
	}

	@GetMapping("/appointments/{appointmentId}/notifyPatient")
	public String notifyPatient(@PathVariable Long appointmentId) {
		logger.info("Sending patient notification for Appointment ID: {}", appointmentId);
		return service.notifyPatientAboutAppointment(appointmentId);
	}

	@GetMapping("/appointments/{appointmentId}/notifyDoctor")
	public String notifyDoctor(@PathVariable Long appointmentId) {
		logger.info("Sending doctor notification for Appointment ID: {}", appointmentId);
		return service.notifyDoctorAboutAppointment(appointmentId);
	}

}