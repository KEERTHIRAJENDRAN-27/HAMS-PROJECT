package com.hams.notification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.hams.notification.dto.NotificationDTO;
import com.hams.notification.dto.PatientProfile;
import com.hams.notification.exception.PatientIDNotFoundException;
import com.hams.notification.feignclient.PatientClient;
import com.hams.notification.model.Notification;
import com.hams.notification.repository.NotificationRepository;
import com.hams.notification.service.NotificationServiceImpl;

@SpringBootTest
class NotificationServiceApplicationTests {
	@Mock
	private NotificationRepository repository;

	@Mock
	private PatientClient patientClient;

	@InjectMocks
	private NotificationServiceImpl service;

	private NotificationDTO notificationDTO;
	private Notification notification;
	private PatientProfile mockPatient;

	@BeforeEach
	void setUp() {
		notificationDTO = new NotificationDTO();
		notificationDTO.setPatientId(1L);
		notificationDTO.setMessage("Appointment reminder");
		notificationDTO.setTimestamp(LocalDateTime.now());

		notification = new Notification();
		notification.setPatientId(1L);
		notification.setMessage("Appointment reminder");
		notification.setTimestamp(LocalDateTime.now());

		mockPatient = new PatientProfile();
		mockPatient.setName("John Doe");
	}

	@Test
	void testSendNotification_Success() {
		when(patientClient.getPatientById(1L)).thenReturn(mockPatient);
		when(patientClient.getEmailById(1L)).thenReturn("john.doe@example.com");
		when(repository.save(any(Notification.class))).thenReturn(notification);

		String result = service.sendNotification(notificationDTO);
		assertEquals("Notification sent to patient ID: 1", result);
	}

	@Test
	void testSendNotification_InvalidPatient() {
		when(patientClient.getPatientById(1L)).thenThrow(new PatientIDNotFoundException("Invalid Patient ID: 1"));

		assertThrows(PatientIDNotFoundException.class, () -> service.sendNotification(notificationDTO));
	}

	@Test
	void testSendNotification_EmailFetchFailure() {
		when(patientClient.getPatientById(1L)).thenReturn(mockPatient);
		when(patientClient.getEmailById(1L)).thenThrow(new PatientIDNotFoundException("Unable to fetch email"));

		assertThrows(PatientIDNotFoundException.class, () -> service.sendNotification(notificationDTO));
	}

	@Test
	void testGetNotificationsByPatientId_Success() {
		when(repository.findByPatientId(1)).thenReturn(Arrays.asList(notification));

		List<Notification> result = service.getNotificationsByPatientId(1);
		assertFalse(result.isEmpty());
		assertEquals(1, result.get(0).getPatientId());
	}
}
