package com.hams.notification.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hams.notification.dto.AppointmentResponseDTO;
import com.hams.notification.dto.DoctorResponseDTO;
import com.hams.notification.dto.NotificationDTO;
import com.hams.notification.dto.PatientProfile;
import com.hams.notification.feignclient.AppointmentClient;
import com.hams.notification.feignclient.DoctorClient;
import com.hams.notification.feignclient.MedicalHistoryClient;
import com.hams.notification.feignclient.PatientClient;
import com.hams.notification.model.Notification;
import com.hams.notification.repository.NotificationRepository;

import feign.FeignException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository repository;
	private final PatientClient patientClient;
	private final AppointmentClient appointmentClient;
	private final MedicalHistoryClient medicalHistoryClient;
	private final DoctorClient doctorClient;

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	public String notifyPatientAboutAppointment(Long appointmentId) {
		try {
			AppointmentResponseDTO appointment = appointmentClient.getAppointmentById(appointmentId);
			Long patientId = appointment.getPatientId();

			String message = switch (appointment.getStatus().toUpperCase()) {
			case "BOOKED" -> "Your appointment has been successfully booked for " + appointment.getAppointmentDate()
					+ " at " + appointment.getAppointmentTime() + ".";
			case "RESCHEDULED" -> "Your appointment has been rescheduled to " + appointment.getAppointmentDate()
					+ " at " + appointment.getAppointmentTime() + ".";
			case "CANCELLED" -> "Your appointment has been cancelled.";
			default -> "Your appointment status has been updated.";
			};

			return sendNotificationToPatient(patientId, message);
		} catch (FeignException.NotFound e) {
			logger.error("Appointment not found with ID: {}", appointmentId);
			return "Appointment not found with ID: " + appointmentId;
		} catch (Exception e) {
			logger.error("Error notifying patient about appointment: {}", e.getMessage());
			return "Error notifying patient about appointment: " + e.getMessage();
		}
	}

	public String sendNotificationToPatient(Long patientId, String message) {
		try {
			PatientProfile patient = patientClient.getPatientById(patientId);
			Notification notification = new Notification();
			notification.setPatientId(patientId);
			notification.setMessage("Hi " + patient.getName() + ", " + message);
			notification.setTimestamp(LocalDateTime.now());

			repository.save(notification);
			logger.info("Patient notification stored in DB: {}", notification);
			return "Notification sent successfully to Patient ID: " + patientId;
		} catch (FeignException.NotFound e) {
			logger.error("Patient not found with ID: {}", patientId);
			return "Patient not found with ID: " + patientId;
		} catch (Exception e) {
			logger.error("Error sending notification to patient: {}", e.getMessage());
			return "Error sending notification to patient: " + e.getMessage();
		}
	}

	/** Send Notification Manually **/
	@Override
	public NotificationDTO sendNotification(NotificationDTO dto) {
		Notification notification = mapToEntity(dto);
		Notification savedNotification = repository.save(notification);
		return mapToDTO(savedNotification);
	}

	/** Helper Methods for DTO Mapping **/
	private NotificationDTO mapToDTO(Notification notification) {
		return NotificationDTO.builder().notificationId(notification.getNotificationId())
				.patientId(notification.getPatientId()).message(notification.getMessage())
				.timestamp(notification.getTimestamp()).build();
	}

	private Notification mapToEntity(NotificationDTO dto) {
		return Notification.builder().notificationId(dto.getNotificationId()).patientId(dto.getPatientId())
				.message(dto.getMessage())
				.timestamp(dto.getTimestamp() != null ? dto.getTimestamp() : LocalDateTime.now()).build();
	}

	public String notifyDoctorAboutAppointment(Long appointmentId) {
		try {
			AppointmentResponseDTO appointment = appointmentClient.getAppointmentById(appointmentId);
			Long doctorId = appointment.getDoctorId();
			DoctorResponseDTO doctor = doctorClient.getDoctorById(doctorId);

			String message = String.format("Dr. %s, you have an upcoming appointment with patient %s on %s at %s.",
					doctor.getDoctorName(), appointment.getPatientName(), appointment.getAppointmentDate(),
					appointment.getAppointmentTime());

			return sendNotificationToDoctor(doctorId, message);
		} catch (FeignException.NotFound e) {
			logger.error("Appointment or Doctor not found for ID: {}", appointmentId);
			return "Appointment or Doctor not found for ID: " + appointmentId;
		} catch (Exception e) {
			logger.error("Error notifying doctor about appointment: {}", e.getMessage());
			return "Error notifying doctor about appointment: " + e.getMessage();
		}
	}

	public String sendNotificationToDoctor(Long doctorId, String message) {
		try {
			DoctorResponseDTO doctor = doctorClient.getDoctorById(doctorId);
			Notification notification = new Notification();
			notification.setPatientId(doctorId); // ✅ Using doctor ID for notification
			notification.setMessage("Dr. " + doctor.getDoctorName() + ", " + message);
			notification.setTimestamp(LocalDateTime.now());

			repository.save(notification);
			logger.info("Doctor notification stored in DB: {}", notification);
			return "Notification sent successfully to Doctor ID: " + doctorId;
		} catch (FeignException.NotFound e) {
			logger.error("Doctor not found with ID: {}", doctorId);
			return "Doctor not found with ID: " + doctorId;
		} catch (Exception e) {
			logger.error("Error sending notification to doctor: {}", e.getMessage());
			return "Error sending notification to doctor: " + e.getMessage();
		}
	}

	public MedicalHistoryClient getMedicalHistoryClient() {
		return medicalHistoryClient;
	}

	
}