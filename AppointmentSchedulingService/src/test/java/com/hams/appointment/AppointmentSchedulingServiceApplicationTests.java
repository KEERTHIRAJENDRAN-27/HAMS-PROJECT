package com.hams.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.hams.appointment.dto.AppointmentDTO;
import com.hams.appointment.dto.DoctorScheduleDTO;
import com.hams.appointment.dto.PatientDTO;
import com.hams.appointment.exception.AppointmentNotFoundException;
import com.hams.appointment.exception.InvalidPatientException;
import com.hams.appointment.feignclient.DoctorClient;
import com.hams.appointment.feignclient.PatientClient;
import com.hams.appointment.model.Appointment;
import com.hams.appointment.repository.AppointmentRepository;
import com.hams.appointment.service.AppointmentServiceImpl;

@SpringBootTest
class AppointmentSchedulingServiceApplicationTests {

	@Mock
	private AppointmentRepository repository;

	@Mock
	private PatientClient patientClient;

	@Mock
	private DoctorClient doctorClient;

	@InjectMocks
	private AppointmentServiceImpl service;

	private AppointmentDTO appointmentDTO;
	private Appointment appointment;
	private PatientDTO mockPatient;
	private DoctorScheduleDTO mockDoctor;

	@BeforeEach
	void setUp() {
		appointmentDTO = new AppointmentDTO();
		appointmentDTO.setPatientId(1L);
		appointmentDTO.setDoctorId(2L);
		appointmentDTO.setAppointmentDate(LocalDateTime.now());
		appointmentDTO.setStatus("Scheduled");

		appointment = new Appointment();
		appointment.setPatientId(1L);
		appointment.setDoctorId(2L);
		appointment.setAppointmentDate(LocalDateTime.now());
		appointment.setStatus("Scheduled");

		// Mock valid PatientDTO
		mockPatient = new PatientDTO();
		mockPatient.setId(1L);
		mockPatient.setName("John Doe");

		// Mock valid DoctorScheduleDTO
		mockDoctor = new DoctorScheduleDTO();
		mockDoctor.setDoctorId(2L);
		mockDoctor.setDoctorName("Dr. Smith");
	}

	@Test
	void testBookAppointment_Success() {
		when(patientClient.getPatientById(1L)).thenReturn(mockPatient); // Correct type
		when(doctorClient.getDoctorScheduleById(2L)).thenReturn(mockDoctor); // Correct type
		when(repository.save(any(Appointment.class))).thenReturn(appointment);

		Appointment result = service.bookAppointment(appointmentDTO);
		assertNotNull(result);
		assertEquals("Scheduled", result.getStatus());
	}

	@Test
	void testBookAppointment_InvalidPatient() {
		when(patientClient.getPatientById(1L)).thenThrow(new InvalidPatientException("Invalid Patient ID: 1"));

		assertThrows(InvalidPatientException.class, () -> service.bookAppointment(appointmentDTO));
	}

	@Test
	void testUpdateAppointment_Success() {
		when(repository.findById(1L)).thenReturn(Optional.of(appointment));
		when(patientClient.getPatientById(1L)).thenReturn(mockPatient);
		when(doctorClient.getDoctorScheduleById(2L)).thenReturn(mockDoctor);
		when(repository.save(any(Appointment.class))).thenReturn(appointment);

		Appointment updated = service.updateAppointment(1L, appointmentDTO);
		assertNotNull(updated);
		assertEquals("Scheduled", updated.getStatus());
	}

	@Test
	void testUpdateAppointment_NotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(AppointmentNotFoundException.class, () -> service.updateAppointment(1L, appointmentDTO));
	}

	@Test
	void testCancelAppointment_Success() {
		when(repository.findById(1L)).thenReturn(Optional.of(appointment));

		service.cancelAppointment(1L);

		assertEquals("Cancelled", appointment.getStatus());
		verify(repository, times(1)).save(appointment);
	}

	@Test
	void testCancelAppointment_NotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(AppointmentNotFoundException.class, () -> service.cancelAppointment(1L));
	}

	@Test
	void testGetAppointmentById_Success() {
		when(repository.findById(1L)).thenReturn(Optional.of(appointment));

		Appointment result = service.getAppointmentById(1L);
		assertNotNull(result);
		assertEquals(1L, result.getPatientId());
	}

	@Test
	void testGetAppointmentById_NotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(AppointmentNotFoundException.class, () -> service.getAppointmentById(1L));
	}
}
