package com.hams.notification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hams.notification.dto.NotificationDTO;
import com.hams.notification.model.Notification;
import com.hams.notification.service.NotificationService;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	@Autowired
	private NotificationService service;

	@PostMapping("/send")
	public String sendNotification(@RequestBody NotificationDTO dto) {
		return service.sendNotification(dto);
	}

	@GetMapping("/fetchByPatientId/{patientId}")
	public List<Notification> getNotifications(@PathVariable Integer patientId) {
		return service.getNotificationsByPatientId(patientId);
	}
}
