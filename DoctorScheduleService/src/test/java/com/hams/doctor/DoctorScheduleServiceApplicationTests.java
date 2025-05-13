package com.hams.doctor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.hams.doctor.dto.DoctorScheduleDTO;
import com.hams.doctor.exception.DoctorAlreadyExistsException;
import com.hams.doctor.exception.ScheduleNotFoundException;
import com.hams.doctor.model.DoctorSchedule;
import com.hams.doctor.repository.DoctorScheduleRepository;
import com.hams.doctor.service.DoctorScheduleServiceImpl;

@SpringBootTest
class DoctorScheduleServiceApplicationTests {

	@Mock
	private DoctorScheduleRepository repository;

	@InjectMocks
	private DoctorScheduleServiceImpl service;

	private DoctorScheduleDTO scheduleDTO;
	private DoctorSchedule schedule;

	@BeforeEach
	void setUp() {
		scheduleDTO = new DoctorScheduleDTO();
		scheduleDTO.setDoctorId(1L);
		scheduleDTO.setDoctorName("Dr. John Doe");
		scheduleDTO.setSpecialization("Cardiology");
		scheduleDTO.setAvailableTimeSlots(Arrays.asList(LocalDateTime.now()));

		schedule = new DoctorSchedule();
		schedule.setDoctorId(1L);
		schedule.setDoctorName("Dr. John Doe");
		schedule.setSpecialization("Cardiology");
		schedule.setAvailableTimeSlots("2025-05-12T10:00");
	}

	@Test
	void testAddSchedule_Success() {
		when(repository.findByDoctorId(1L)).thenReturn(Optional.empty());
		when(repository.save(any(DoctorSchedule.class))).thenReturn(schedule);

		String result = service.addSchedule(scheduleDTO);
		assertEquals("Doctor schedule registered successfully.", result);
	}

	@Test
	void testAddSchedule_DoctorAlreadyExists() {
		when(repository.findByDoctorId(1L)).thenReturn(Optional.of(schedule));

		assertThrows(DoctorAlreadyExistsException.class, () -> service.addSchedule(scheduleDTO));
	}

	@Test
	void testUpdateSchedule_Success() {
		when(repository.findByDoctorId(1L)).thenReturn(Optional.of(schedule));
		when(repository.save(any(DoctorSchedule.class))).thenReturn(schedule);

		String result = service.updateSchedule(1L, scheduleDTO);
		assertEquals("Doctor schedule updated successfully.", result);
	}

	@Test
	void testUpdateSchedule_NotFound() {
		when(repository.findByDoctorId(1L)).thenReturn(Optional.empty());

		assertThrows(ScheduleNotFoundException.class, () -> service.updateSchedule(1L, scheduleDTO));
	}

	@Test
	void testGetById_Success() {
		when(repository.findById(1L)).thenReturn(Optional.of(schedule));

		DoctorSchedule result = service.getById(1L);
		assertNotNull(result);
		assertEquals(1L, result.getDoctorId());
	}

	@Test
	void testGetById_NotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ScheduleNotFoundException.class, () -> service.getById(1L));
	}

	@Test
	void testGetAll_Success() {
		when(repository.findAll()).thenReturn(Arrays.asList(schedule));

		List<DoctorSchedule> result = service.getAll();
		assertFalse(result.isEmpty());
	}

	@Test
	void testDeleteById_Success() {
		when(repository.findById(1L)).thenReturn(Optional.of(schedule));

		String result = service.deleteById(1L);
		assertEquals("Doctor schedule deleted successfully.", result);
		verify(repository, times(1)).delete(schedule);
	}

	@Test
	void testDeleteById_NotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ScheduleNotFoundException.class, () -> service.deleteById(1L));
	}
}
