package com.hams.notification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.hams.notification.dto.NotificationDTO;
import com.hams.notification.model.Notification;
import com.hams.notification.repository.NotificationRepository;
import com.hams.notification.service.NotificationServiceImpl;

@SpringBootTest
class NotificationServiceApplicationTests {

	private final NotificationRepository repo = mock(NotificationRepository.class);
	private final NotificationServiceImpl service = new NotificationServiceImpl(repo, null, null, null, null);

	@Test
	void testSendNotification() {
		NotificationDTO dto = NotificationDTO.builder().patientId(1L).message("Your appointment is tomorrow.")
				.timestamp(LocalDateTime.now()).build();

		Notification entity = Notification.builder().notificationId(1L).patientId(1L)
				.message("Your appointment is tomorrow.").timestamp(LocalDateTime.now()).build();

		when(repo.save(any())).thenReturn(entity);

		NotificationDTO result = service.sendNotification(dto);
		assertEquals("Your appointment is tomorrow.", result.getMessage());
	}

}
