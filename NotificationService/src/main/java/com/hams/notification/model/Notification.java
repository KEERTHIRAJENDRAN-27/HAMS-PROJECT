package com.hams.notification.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Entity
@Table(name = "notification")
@Builder
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notificationId;

	@NotNull(message = "Patient ID is required")
	private Long patientId;

	@NotBlank(message = "Message content cannot be empty")
	@Size(max = 500, message = "Message should not exceed 500 characters")
	private String message;

	@PastOrPresent(message = "Timestamp must be in the past or present")
	private LocalDateTime timestamp;

	public Notification() {
		// TODO Auto-generated constructor stub
	}

	public Notification(Long notificationId, @NotNull(message = "Patient ID is required") Long patientId,
			@NotBlank(message = "Message content cannot be empty") @Size(max = 500, message = "Message should not exceed 500 characters") String message,
			@PastOrPresent(message = "Timestamp must be in the past or present") LocalDateTime timestamp) {
		super();
		this.notificationId = notificationId;
		this.patientId = patientId;
		this.message = message;
		this.timestamp = timestamp;
	}

	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}