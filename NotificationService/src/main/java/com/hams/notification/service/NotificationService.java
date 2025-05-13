package com.hams.notification.service;

import java.util.List;

import com.hams.notification.dto.NotificationDTO;
import com.hams.notification.model.Notification;

public interface NotificationService {
	String sendNotification(NotificationDTO dto);

	List<Notification> getNotificationsByPatientId(Integer patientId);
}
