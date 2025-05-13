package com.hams.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.hams.patient.exception.PatientNotFoundException;
import com.hams.patient.model.Patient;
import com.hams.patient.repository.PatientRepository;
import com.hams.patient.service.PatientServiceImpl;

@SpringBootTest
class PatientProfileServiceApplicationTests {
	@Mock
	private PatientRepository repository;

	@InjectMocks
	private PatientServiceImpl service;

	private Patient mockPatient;

	@BeforeEach
	void setUp() {
		mockPatient = new Patient();
		mockPatient.setName("John Doe");
		mockPatient.setAge(30);
		mockPatient.setBloodGroup("O+");
		mockPatient.setGender("Male");
		mockPatient.setEmail("john.doe@example.com");
	}

	@Test
	void testRegisterPatient_Success() {
		when(repository.save(any(Patient.class))).thenReturn(mockPatient);

		String result = service.registerPatient(mockPatient);
		assertEquals("Patient registered successfully.", result);
	}

	@Test
	void testUpdatePatient_Success() {
		when(repository.findById(1L)).thenReturn(Optional.of(mockPatient));
		when(repository.save(any(Patient.class))).thenReturn(mockPatient);

		String result = service.updatePatient(1L, mockPatient);
		assertEquals("Patient updated successfully.", result);
	}

	@Test
	void testUpdatePatient_NotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(PatientNotFoundException.class, () -> service.updatePatient(1L, mockPatient));
	}

	@Test
	void testGetPatientById_Success() {
		when(repository.findById(1L)).thenReturn(Optional.of(mockPatient));

		Patient result = service.getPatientById(1L);
		assertNotNull(result);
		assertEquals("John Doe", result.getName());
	}

	@Test
	void testGetPatientById_NotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(PatientNotFoundException.class, () -> service.getPatientById(1L));
	}

	@Test
	void testGetAllPatients_Success() {
		when(repository.findAll()).thenReturn(Arrays.asList(mockPatient));

		List<Patient> result = service.getAllPatients();
		assertFalse(result.isEmpty());
	}

	@Test
	void testDeletePatient_Success() {
		when(repository.existsById(1L)).thenReturn(true);
		doNothing().when(repository).deleteById(1L);

		String result = service.deletePatient(1L);
		assertEquals("Patient with ID 1 has been deleted successfully.", result);
		verify(repository, times(1)).deleteById(1L);
	}

	@Test
	void testDeletePatient_NotFound() {
		when(repository.existsById(1L)).thenReturn(false);

		assertThrows(PatientNotFoundException.class, () -> service.deletePatient(1L));
	}
}
