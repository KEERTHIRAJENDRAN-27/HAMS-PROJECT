//package com.hams.appointment;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.hams.appointment.dto.AppointmentDTO;
//import com.hams.appointment.dto.AppointmentPatientRequestDTO;
//import com.hams.appointment.dto.DoctorScheduleToAppointmentDTO;
//import com.hams.appointment.dto.PatientProfile;
//import com.hams.appointment.exception.InvalidDoctorException;
//import com.hams.appointment.exception.InvalidPatientException;
//import com.hams.appointment.feignclient.DoctorClient;
//import com.hams.appointment.feignclient.PatientClient;
//import com.hams.appointment.model.Appointment;
//import com.hams.appointment.repository.AppointmentRepository;
//import com.hams.appointment.service.AppointmentServiceImpl;
//
//@SpringBootTest
//class AppointmentSchedulingServiceApplicationTests {
//	@Mock
//	private AppointmentRepository repo;
//
//	@Mock
//	private PatientClient patientClient;
//
//	@Mock
//	private DoctorClient doctorClient;
//
//	@InjectMocks
//	private AppointmentServiceImpl appointmentService;
//
//	@BeforeEach
//	public void setUp() {
//		MockitoAnnotations.openMocks(this);
//	}
//
//	@Test
//	public void testSaveAppointment() {
//		AppointmentDTO appointmentDTO = new AppointmentDTO();
//		appointmentDTO.setPatientId(1);
//		appointmentDTO.setDoctorId(1);
//		appointmentDTO.setAppointmentTime("2025-05-14T10:00:00");
//		appointmentDTO.setReason("Checkup");
//		appointmentDTO.setStatus("Scheduled");
//
//		AppointmentPatientRequestDTO requestDTO = new AppointmentPatientRequestDTO();
//		requestDTO.setAppointment(appointmentDTO);
//
//		when(patientClient.getPatientById(1)).thenReturn(new PatientProfile());
//		when(doctorClient.getDoctorById(1)).thenReturn(new DoctorScheduleToAppointmentDTO());
//
//		String result = appointmentService.saveAppointment(requestDTO);
//
//		assertEquals("Appointment Saved", result);
//		verify(repo).save(new Appointment(1, 1, "2025-05-14T10:00:00", "Checkup", "Scheduled"));
//	}
//
//	@Test
//	public void testSaveAppointmentInvalidPatient() {
//		AppointmentDTO appointmentDTO = new AppointmentDTO();
//		appointmentDTO.setPatientId(1);
//		appointmentDTO.setDoctorId(1);
//		appointmentDTO.setAppointmentTime("2025-05-14T10:00:00");
//		appointmentDTO.setReason("Checkup");
//		appointmentDTO.setStatus("Scheduled");
//
//		AppointmentPatientRequestDTO requestDTO = new AppointmentPatientRequestDTO();
//		requestDTO.setAppointment(appointmentDTO);
//
//		doThrow(new InvalidPatientException("Invalid Patient ID: 1")).when(patientClient).getPatientById(1);
//
//		assertThrows(InvalidPatientException.class, () -> appointmentService.saveAppointment(requestDTO));
//	}
//
//	@Test
//	public void testSaveAppointmentInvalidDoctor() {
//		AppointmentDTO appointmentDTO = new AppointmentDTO();
//		appointmentDTO.setPatientId(1);
//		appointmentDTO.setDoctorId(1);
//		appointmentDTO.setAppointmentTime("2025-05-14T10:00:00");
//		appointmentDTO.setReason("Checkup");
//		appointmentDTO.setStatus("Scheduled");
//
//		AppointmentPatientRequestDTO requestDTO = new AppointmentPatientRequestDTO();
//		requestDTO.setAppointment(appointmentDTO);
//
//		when(patientClient.getPatientById(1)).thenReturn(new PatientProfile());
//		doThrow(new InvalidDoctorException("Invalid Doctor ID: 1")).when(doctorClient).getDoctorById(1);
//
//		assertThrows(InvalidDoctorException.class, () -> appointmentService.saveAppointment(requestDTO));
//	}
//}
