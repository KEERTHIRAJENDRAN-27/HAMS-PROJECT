package com.hams.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hams.notification.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
	List<Notification> findByPatientId(Integer patientId);
}
