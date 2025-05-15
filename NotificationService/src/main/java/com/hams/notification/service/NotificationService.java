package com.hams.notification.service;

import com.hams.notification.dto.NotificationDTO;

public interface NotificationService {
	NotificationDTO sendNotification(NotificationDTO dto);

	String notifyPatientAboutAppointment(Long appointmentId);

	public String notifyDoctorAboutAppointment(Long appointmentId);

}