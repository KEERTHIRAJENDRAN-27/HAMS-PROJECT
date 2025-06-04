package com.hams.notify.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hams.notify.dto.EmailRequest;
import com.hams.notify.service.NotificationService;

@RestController
@RequestMapping("/email")
public class NotificationController {
//	@Autowired
//	private NotificationService notificationService;
//
//	@PostMapping("/send")
//	public String sendEmail(@RequestParam String recipient, @RequestParam String subject,
//			@RequestParam String message) {
//		return notificationService.sendEmail(recipient, subject, message);
//	}

	@Autowired
	private NotificationService notificationService;

	@PostMapping("/send")
	public String sendEmail(@RequestBody EmailRequest emailRequest) {
		return notificationService.sendEmail(emailRequest.getRecipient(), emailRequest.getSubject(),
				emailRequest.getMessage());
	}
}
