package com.hams.medical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.hams.medical.dto.MedicalHistoryPatientResponseDTO;
import com.hams.medical.dto.Patient;
import com.hams.medical.exception.HistoryNotFoundException;
import com.hams.medical.exception.PatientNotFoundException;
import com.hams.medical.feignclient.PatientClient;
import com.hams.medical.model.MedicalHistory;
import com.hams.medical.repository.MedicalHistoryRepository;
import com.hams.medical.service.MedicalHistoryServiceImpl;

@SpringBootTest
class MedicalHistoryServiceApplicationTests {

	@Mock
	private MedicalHistoryRepository repository;

	@Mock
	private PatientClient patientClient;

	@InjectMocks
	private MedicalHistoryServiceImpl service;

	private MedicalHistory mockHistory;
	private Patient mockPatient;

	@BeforeEach
	void setUp() {
		mockPatient = new Patient();
//		mockPatient.setId(1L);
		mockPatient.setName("John Doe");

		mockHistory = new MedicalHistory();
		mockHistory.setPatientId(1L);
		mockHistory.setDiagnosis("Flu");
		mockHistory.setTreatment("Rest and hydration");
	}

	@Test
	void testGetMedicalHistoryWithPatient_Success() {
		when(patientClient.getPatientById(1L)).thenReturn(mockPatient);
		when(repository.findByPatientId(1L)).thenReturn(Arrays.asList(mockHistory));

		MedicalHistoryPatientResponseDTO result = service.getMedicalHistoryWithPatient(1L);
		assertNotNull(result);
		assertEquals("John Doe", result.getPatient().getName());
	}

	@Test
	void testGetMedicalHistoryWithPatient_PatientNotFound() {
		when(patientClient.getPatientById(1L)).thenThrow(new PatientNotFoundException("Patient not found"));

		assertThrows(PatientNotFoundException.class, () -> service.getMedicalHistoryWithPatient(1L));
	}

	@Test
	void testSaveHistory_Success() {
		when(patientClient.getPatientById(1L)).thenReturn(mockPatient);
		when(repository.save(any(MedicalHistory.class))).thenReturn(mockHistory);

		String result = service.saveHistory(mockHistory);
		assertEquals("Medical history saved successfully.", result);
	}

	@Test
	void testSaveHistory_InvalidPatient() {
		when(patientClient.getPatientById(1L)).thenThrow(new PatientNotFoundException("Invalid Patient ID"));

		assertThrows(PatientNotFoundException.class, () -> service.saveHistory(mockHistory));
	}

	@Test
	void testUpdateHistory_Success() {
		when(repository.findById(1L)).thenReturn(Optional.of(mockHistory));
		when(repository.save(any(MedicalHistory.class))).thenReturn(mockHistory);

		String result = service.updateHistory(1L, mockHistory);
		assertEquals("Medical history updated successfully.", result);
	}

	@Test
	void testUpdateHistory_NotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(HistoryNotFoundException.class, () -> service.updateHistory(1L, mockHistory));
	}

	@Test
	void testGetById_Success() {
		when(repository.findById(1L)).thenReturn(Optional.of(mockHistory));

		MedicalHistory result = service.getById(1L);
		assertNotNull(result);
		assertEquals("Flu", result.getDiagnosis());
	}

	@Test
	void testGetById_NotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(HistoryNotFoundException.class, () -> service.getById(1L));
	}

	@Test
	void testDeleteById_Success() {
		when(repository.existsById(1L)).thenReturn(true);
		doNothing().when(repository).deleteById(1L);

		String result = service.deleteById(1L);
		assertEquals("Medical history deleted successfully.", result);
		verify(repository, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteById_NotFound() {
		when(repository.existsById(1L)).thenReturn(false);

		assertThrows(HistoryNotFoundException.class, () -> service.deleteById(1L));
	}
}
