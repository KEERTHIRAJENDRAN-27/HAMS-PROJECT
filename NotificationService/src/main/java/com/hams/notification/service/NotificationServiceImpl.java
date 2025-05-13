package com.hams.notification.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hams.notification.dto.NotificationDTO;
import com.hams.notification.dto.PatientProfile;
import com.hams.notification.exception.PatientIDNotFoundException;
import com.hams.notification.feignclient.PatientClient;
import com.hams.notification.model.Notification;
import com.hams.notification.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private NotificationRepository repository;

	@Autowired
	private PatientClient patientClient;

	@Override
	public String sendNotification(NotificationDTO dto) {
		logger.info("Attempting to send notification for patient ID: {}", dto.getPatientId());

		try {
			PatientProfile patient = patientClient.getPatientById(dto.getPatientId());
			logger.info("Patient profile retrieved for ID: {}", dto.getPatientId());
		} catch (Exception e) {
			logger.error("Invalid Patient ID: {}", dto.getPatientId(), e);
			throw new PatientIDNotFoundException("Invalid Patient ID: " + dto.getPatientId());
		}

		try {
			String email = patientClient.getEmailById(dto.getPatientId());
			logger.info("Fetched email {} for patient ID: {}", email, dto.getPatientId());

			Notification notification = new Notification();
			notification.setPatientId(dto.getPatientId());
			notification.setMessage(dto.getMessage());
			notification.setTimestamp(dto.getTimestamp());

			repository.save(notification);
			logger.info("Notification saved successfully for patient ID: {}", dto.getPatientId());

			// Mock sending email log
			logger.info("Email sent to {} with message: {}", email, dto.getMessage());
		} catch (Exception e) {
			logger.error("Unable to fetch email for patient ID: {}", dto.getPatientId(), e);
			throw new PatientIDNotFoundException("Unable to fetch email for patient ID: " + dto.getPatientId());
		}

		return "Notification sent to patient ID: " + dto.getPatientId();
	}

	@Override
	public List<Notification> getNotificationsByPatientId(Integer patientId) {
		logger.info("Fetching notifications for patient ID: {}", patientId);
		return repository.findByPatientId(patientId);
	}
}
