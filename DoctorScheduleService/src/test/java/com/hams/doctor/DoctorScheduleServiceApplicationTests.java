package com.hams.doctor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.hams.doctor.dto.DoctorScheduleDTO;
import com.hams.doctor.exception.DoctorAlreadyExistsException;
import com.hams.doctor.exception.ScheduleNotFoundException;
import com.hams.doctor.feignclient.AppointmentClient;
import com.hams.doctor.model.DoctorSchedule;
import com.hams.doctor.repository.DoctorScheduleRepository;
import com.hams.doctor.service.DoctorScheduleServiceImpl;

@SpringBootTest
class DoctorScheduleServiceApplicationTests {

	@InjectMocks
	private DoctorScheduleServiceImpl doctorScheduleService;

	@Mock
	private DoctorScheduleRepository doctorScheduleRepository;

	@Mock
	private AppointmentClient appointmentClient;

	private DoctorScheduleDTO doctorScheduleDTO;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

		// Set up sample data for test
		doctorScheduleDTO = new DoctorScheduleDTO();
		doctorScheduleDTO.setDoctorId(1L);
		doctorScheduleDTO.setDoctorName("Dr. Smith");
		doctorScheduleDTO.setSpecialization("Cardiology");
		doctorScheduleDTO.setAvailableDays(Arrays.asList("Monday", "Tuesday"));
		doctorScheduleDTO.setAvailableTime("09:00 - 17:00");
	}

	@Test
	public void testAddSchedule_Success() {
		DoctorSchedule schedule = new DoctorSchedule();
		schedule.setDoctorId(1L);
		when(doctorScheduleRepository.findByDoctorId(1L)).thenReturn(Optional.empty());
		when(doctorScheduleRepository.save(any(DoctorSchedule.class))).thenReturn(schedule);

		String result = doctorScheduleService.addSchedule(doctorScheduleDTO);

		assertEquals("Doctor schedule added successfully", result);
	}

	@Test
	public void testAddSchedule_DoctorAlreadyExists() {
		DoctorSchedule schedule = new DoctorSchedule();
		schedule.setDoctorId(1L);
		when(doctorScheduleRepository.findByDoctorId(1L)).thenReturn(Optional.of(schedule));

		assertThrows(DoctorAlreadyExistsException.class, () -> {
			doctorScheduleService.addSchedule(doctorScheduleDTO);
		});
	}

	@Test
	public void testUpdateSchedule_Success() {
		DoctorSchedule existingSchedule = new DoctorSchedule();
		existingSchedule.setDoctorId(1L);
		when(doctorScheduleRepository.findById(1L)).thenReturn(Optional.of(existingSchedule));

		String result = doctorScheduleService.updateSchedule(1L, doctorScheduleDTO);

		assertEquals("Doctor schedule updated successfully", result);
	}

	@Test
	public void testUpdateSchedule_ScheduleNotFound() {
		when(doctorScheduleRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ScheduleNotFoundException.class, () -> {
			doctorScheduleService.updateSchedule(1L, doctorScheduleDTO);
		});
	}

	@Test
	public void testIsDoctorAvailable_Success() {
		DoctorSchedule schedule = new DoctorSchedule();
		schedule.setDoctorId(1L);
		schedule.setAvailableDays(Arrays.asList("Monday", "Tuesday"));
		schedule.setAvailableTime("09:00 - 17:00");
		when(doctorScheduleRepository.findByDoctorId(1L)).thenReturn(Optional.of(schedule));

		LocalDateTime requestedTime = LocalDateTime.of(2025, 5, 13, 10, 0, 0, 0); // Tuesday, 10 AM
		boolean isAvailable = doctorScheduleService.isDoctorAvailable(1L, requestedTime);

		assertTrue(isAvailable);
	}

	@Test
	public void testIsDoctorAvailable_NotAvailable() {
		DoctorSchedule schedule = new DoctorSchedule();
		schedule.setDoctorId(1L);
		schedule.setAvailableDays(Arrays.asList("Monday", "Tuesday"));
		schedule.setAvailableTime("09:00 - 17:00");
		when(doctorScheduleRepository.findByDoctorId(1L)).thenReturn(Optional.of(schedule));

		LocalDateTime requestedTime = LocalDateTime.of(2025, 5, 13, 18, 0, 0, 0); // Tuesday, 6 PM
		boolean isAvailable = doctorScheduleService.isDoctorAvailable(1L, requestedTime);

		assertFalse(isAvailable);
	}

	@Test
	public void testGetDoctorById_Success() {
		DoctorSchedule schedule = new DoctorSchedule();
		schedule.setDoctorId(1L);
		when(doctorScheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

		DoctorSchedule result = doctorScheduleService.getById(1L);

		assertEquals(1L, result.getDoctorId());
	}

	@Test
	public void testGetDoctorById_ScheduleNotFound() {
		when(doctorScheduleRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ScheduleNotFoundException.class, () -> {
			doctorScheduleService.getById(1L);
		});
	}
}
