package com.hams.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import com.hams.appointment.dto.AppointmentDTO;
import com.hams.appointment.dto.AppointmentPatientRequestDTO;
import com.hams.appointment.dto.AppointmentPatientResponseDTO;
import com.hams.appointment.dto.DoctorScheduleToAppointmentDTO;
import com.hams.appointment.dto.PatientProfile;
import com.hams.appointment.exception.AppointmentNotFoundException;
import com.hams.appointment.exception.DoctorNotAvailableException;
import com.hams.appointment.exception.InvalidDoctorException;
import com.hams.appointment.exception.InvalidPatientException;
import com.hams.appointment.feignclient.DoctorClient;
import com.hams.appointment.feignclient.PatientClient;
import com.hams.appointment.model.Appointment;
import com.hams.appointment.repository.AppointmentRepository;
import com.hams.appointment.service.AppointmentServiceImpl;

@SpringBootTest
class AppointmentSchedulingServiceApplicationTests {
	@Mock
	private AppointmentRepository repo;

	@Mock
	private PatientClient patientClient;

	@Mock
	private DoctorClient doctorClient;

	@InjectMocks
	private AppointmentServiceImpl service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSaveAppointment() {
		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setPatientId(1L);
		appointmentDTO.setDoctorId(1L);
		appointmentDTO.setAppointmentDateTime(LocalDateTime.of(2023, 5, 14, 10, 0));
		appointmentDTO.setReason("Checkup");
		appointmentDTO.setStatus("Scheduled");

		AppointmentPatientRequestDTO requestDTO = new AppointmentPatientRequestDTO();
		requestDTO.setAppointment(appointmentDTO);

		PatientProfile patientProfile = new PatientProfile();
		DoctorScheduleToAppointmentDTO doctorSchedule = new DoctorScheduleToAppointmentDTO();
		doctorSchedule.setAvailableDays(List.of("Monday"));
		doctorSchedule.setAvailableTime("09:00 - 17:00");

		when(patientClient.getPatientById(1L)).thenReturn(patientProfile);
		when(doctorClient.getDoctorById(1L)).thenReturn(doctorSchedule);
		when(repo.findByDoctorIdAndAppointmentDateTime(1L, LocalDateTime.of(2023, 5, 14, 10, 0))).thenReturn(List.of());
		when(repo.findByPatientIdAndAppointmentDateTime(1L, LocalDateTime.of(2023, 5, 14, 10, 0)))
				.thenReturn(List.of());

		String result = service.saveAppointment(requestDTO);

		assertEquals("Appointment Saved Successfully", result);
		verify(repo, times(1)).save(any(Appointment.class));
	}

	@Test
	void testSaveAppointmentInvalidPatient() {
		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setPatientId(1L);
		appointmentDTO.setDoctorId(1L);
		appointmentDTO.setAppointmentDateTime(LocalDateTime.of(2023, 5, 14, 10, 0));
		appointmentDTO.setReason("Checkup");
		appointmentDTO.setStatus("Scheduled");

		AppointmentPatientRequestDTO requestDTO = new AppointmentPatientRequestDTO();
		requestDTO.setAppointment(appointmentDTO);

		when(patientClient.getPatientById(1L)).thenThrow(new RuntimeException());

		assertThrows(InvalidPatientException.class, () -> service.saveAppointment(requestDTO));
	}

	@Test
	void testSaveAppointmentInvalidDoctor() {
		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setPatientId(1L);
		appointmentDTO.setDoctorId(1L);
		appointmentDTO.setAppointmentDateTime(LocalDateTime.of(2023, 5, 14, 10, 0));
		appointmentDTO.setReason("Checkup");
		appointmentDTO.setStatus("Scheduled");

		AppointmentPatientRequestDTO requestDTO = new AppointmentPatientRequestDTO();
		requestDTO.setAppointment(appointmentDTO);

		PatientProfile patientProfile = new PatientProfile();

		when(patientClient.getPatientById(1L)).thenReturn(patientProfile);
		when(doctorClient.getDoctorById(1L)).thenThrow(new RuntimeException());

		assertThrows(InvalidDoctorException.class, () -> service.saveAppointment(requestDTO));
	}

	@Test
	void testSaveAppointmentDoctorNotAvailable() {
		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setPatientId(1L);
		appointmentDTO.setDoctorId(1L);
		appointmentDTO.setAppointmentDateTime(LocalDateTime.of(2023, 5, 14, 10, 0));
		appointmentDTO.setReason("Checkup");
		appointmentDTO.setStatus("Scheduled");

		AppointmentPatientRequestDTO requestDTO = new AppointmentPatientRequestDTO();
		requestDTO.setAppointment(appointmentDTO);

		PatientProfile patientProfile = new PatientProfile();
		DoctorScheduleToAppointmentDTO doctorSchedule = new DoctorScheduleToAppointmentDTO();
		doctorSchedule.setAvailableDays(List.of("Tuesday")); // Doctor not available on Monday
		doctorSchedule.setAvailableTime("09:00 - 17:00");

		when(patientClient.getPatientById(1L)).thenReturn(patientProfile);
		when(doctorClient.getDoctorById(1L)).thenReturn(doctorSchedule);

		assertThrows(DoctorNotAvailableException.class, () -> service.saveAppointment(requestDTO));
	}

	@Test
	void testUpdateAppointment() {
		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setPatientId(1L);
		appointmentDTO.setDoctorId(1L);
		appointmentDTO.setAppointmentDateTime(LocalDateTime.of(2023, 5, 14, 10, 0));
		appointmentDTO.setReason("Checkup");
		appointmentDTO.setStatus("Scheduled");

		Appointment existingAppointment = new Appointment();
		existingAppointment.setId(1L);
		String result = service.updateAppointment(1L, appointmentDTO);

		assertEquals("Appointment Updated", result);
		verify(repo, times(1)).save(existingAppointment);
	}

	@Test
	void testUpdateAppointmentNotFound() {
		AppointmentDTO appointmentDTO = new AppointmentDTO();

		assertThrows(AppointmentNotFoundException.class, () -> service.updateAppointment(1L, appointmentDTO));
	}

	@Test
	void testGetAllAppointments() {
		Appointment appointment = new Appointment();
		appointment.setId(1L);
		appointment.setPatientId(1L);
		appointment.setDoctorId(1L);
		appointment.setAppointmentDateTime(LocalDateTime.of(2023, 5, 14, 10, 0));
		appointment.setReason("Checkup");
		appointment.setStatus("Scheduled");

		when(repo.findAll()).thenReturn(List.of(appointment));

		PatientProfile patientProfile = new PatientProfile();
		DoctorScheduleToAppointmentDTO doctorSchedule = new DoctorScheduleToAppointmentDTO();

		when(patientClient.getPatientById(1L)).thenReturn(patientProfile);
		when(doctorClient.getDoctorById(1L)).thenReturn(doctorSchedule);

		List<AppointmentPatientResponseDTO> result = service.getAllAppointments();

		assertEquals(1, result.size());
		assertEquals(1L, result.get(0).getAppointment().getId());
	}

	@Test
	void testGetAppointmentById() {
		Appointment appointment = new Appointment();
		appointment.setId(1L);
		appointment.setPatientId(1L);
		appointment.setDoctorId(1L);
		appointment.setAppointmentDateTime(LocalDateTime.of(2023, 5, 14, 10, 0));
		appointment.setReason("Checkup");
		appointment.setStatus("Scheduled");

		PatientProfile patientProfile = new PatientProfile();
		DoctorScheduleToAppointmentDTO doctorSchedule = new DoctorScheduleToAppointmentDTO();

		when(patientClient.getPatientById(1L)).thenReturn(patientProfile);
		when(doctorClient.getDoctorById(1L)).thenReturn(doctorSchedule);

		AppointmentPatientResponseDTO result = service.getAppointmentById(1L);

		assertEquals(1L, result.getAppointment().getId());
	}

	@Test
	void testGetAppointmentByIdNotFound() {

		assertThrows(AppointmentNotFoundException.class, () -> service.getAppointmentById(1L));
	}
}
