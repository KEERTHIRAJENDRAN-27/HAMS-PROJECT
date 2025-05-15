package com.hams.doctor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hams.doctor.dto.BookedAppointmentDTO;
import com.hams.doctor.dto.DoctorScheduleDTO;
import com.hams.doctor.exception.DoctorAlreadyExistsException;
import com.hams.doctor.exception.DoctorNotFoundException;
import com.hams.doctor.exception.ScheduleNotFoundException;
import com.hams.doctor.feignclient.AppointmentClient;
import com.hams.doctor.model.DoctorSchedule;
import com.hams.doctor.repository.DoctorScheduleRepository;
import com.hams.doctor.service.DoctorScheduleServiceImpl;

class DoctorScheduleServiceImplTest {
	@Mock
	private DoctorScheduleRepository repository;

	@Mock
	private AppointmentClient appointmentClient;

	@InjectMocks
	private DoctorScheduleServiceImpl service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddSchedule() {
		DoctorScheduleDTO dto = new DoctorScheduleDTO();
		dto.setDoctorId(1L);
		dto.setDoctorName("Dr. Smith");
		dto.setSpecialization("Cardiology");
		dto.setAvailableDays(List.of("Monday", "Wednesday"));
		dto.setAvailableTime("09:00 - 17:00");

		when(repository.findByDoctorId(dto.getDoctorId())).thenReturn(Optional.empty());

		String result = service.addSchedule(dto);

		assertEquals("Doctor schedule added successfully", result);
		verify(repository, times(1)).save(any(DoctorSchedule.class));
	}

	@Test
	void testAddScheduleAlreadyExists() {
		DoctorScheduleDTO dto = new DoctorScheduleDTO();
		dto.setDoctorId(1L);

		when(repository.findByDoctorId(dto.getDoctorId())).thenReturn(Optional.of(new DoctorSchedule()));

		assertThrows(DoctorAlreadyExistsException.class, () -> service.addSchedule(dto));
	}

	@Test
	void testUpdateSchedule() {
		Long doctorId = 1L;
		DoctorScheduleDTO dto = new DoctorScheduleDTO();
		dto.setDoctorName("Dr. Smith");
		dto.setSpecialization("Cardiology");
		dto.setAvailableDays(List.of("Monday", "Wednesday"));
		dto.setAvailableTime("09:00 - 17:00");

		DoctorSchedule existingSchedule = new DoctorSchedule();
		existingSchedule.setDoctorId(doctorId);

		when(repository.findByDoctorId(doctorId)).thenReturn(Optional.of(existingSchedule));

		String result = service.updateSchedule(doctorId, dto);

		assertEquals("Doctor schedule updated successfully for doctor ID: " + doctorId, result);
		verify(repository, times(1)).save(existingSchedule);
	}

	@Test
	void testUpdateScheduleNotFound() {
		Long doctorId = 1L;
		DoctorScheduleDTO dto = new DoctorScheduleDTO();

		when(repository.findByDoctorId(doctorId)).thenReturn(Optional.empty());

		assertThrows(ScheduleNotFoundException.class, () -> service.updateSchedule(doctorId, dto));
	}

	@Test
	void testGetById() {
		Long doctorId = 1L;
		DoctorSchedule schedule = new DoctorSchedule();
		schedule.setDoctorId(doctorId);

		when(repository.findById(doctorId)).thenReturn(Optional.of(schedule));

		DoctorSchedule result = service.getById(doctorId);

		assertEquals(schedule, result);
	}

	@Test
	void testGetByIdNotFound() {
		Long doctorId = 1L;

		when(repository.findById(doctorId)).thenReturn(Optional.empty());

		assertThrows(ScheduleNotFoundException.class, () -> service.getById(doctorId));
	}

	@Test
	void testDeleteById() {
		Long doctorId = 1L;
		DoctorSchedule schedule = new DoctorSchedule();
		schedule.setDoctorId(doctorId);

		when(repository.findById(doctorId)).thenReturn(Optional.of(schedule));

		String result = service.deleteById(doctorId);

		assertEquals("Doctor schedule deleted successfully", result);
		verify(repository, times(1)).deleteById(doctorId);
	}

	@Test
	void testDeleteByIdNotFound() {
		Long doctorId = 1L;

		when(repository.findById(doctorId)).thenReturn(Optional.empty());

		assertThrows(ScheduleNotFoundException.class, () -> service.deleteById(doctorId));
	}

	@Test
	void testIsDoctorAvailableNotAvailable() {
		Long doctorId = 1L;
		LocalDateTime requestedTime = LocalDateTime.of(2023, 5, 14, 18, 0);
		DoctorSchedule schedule = new DoctorSchedule();
		schedule.setDoctorId(doctorId);
		schedule.setAvailableDays(List.of("Monday"));
		schedule.setAvailableTime("09:00 - 17:00");

		when(repository.findByDoctorId(doctorId)).thenReturn(Optional.of(schedule));

		boolean result = service.isDoctorAvailable(doctorId, requestedTime);

		assertFalse(result);
	}

	@Test
	void testGetDoctorBookedAppointments() {
		Long doctorId = 1L;
		List<BookedAppointmentDTO> appointments = List.of(new BookedAppointmentDTO());

		when(appointmentClient.getBookedAppointmentsByDoctorId(doctorId)).thenReturn(appointments);

		List<BookedAppointmentDTO> result = service.getDoctorBookedAppointments(doctorId);

		assertEquals(appointments, result);
	}

	@Test
	void testGetScheduleByDoctorId() {
		Long doctorId = 1L;
		DoctorSchedule schedule = new DoctorSchedule();
		schedule.setDoctorId(doctorId);

		when(repository.findByDoctorId(doctorId)).thenReturn(Optional.of(schedule));

		DoctorSchedule result = service.getScheduleByDoctorId(doctorId);

		assertEquals(schedule, result);
	}

	@Test
	void testGetScheduleByDoctorIdNotFound() {
		Long doctorId = 1L;

		when(repository.findByDoctorId(doctorId)).thenReturn(Optional.empty());

		assertThrows(DoctorNotFoundException.class, () -> service.getScheduleByDoctorId(doctorId));
	}

	@Test
	void testGetAll() {
		List<DoctorSchedule> schedules = List.of(new DoctorSchedule());

		when(repository.findAll()).thenReturn(schedules);

		List<DoctorSchedule> result = service.getAll();

		assertEquals(schedules, result);
	}
}
