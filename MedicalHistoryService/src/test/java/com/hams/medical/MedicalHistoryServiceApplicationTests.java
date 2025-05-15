package com.hams.medical;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.hams.medical.dto.MedicalHistoryPatientResponseDTO;
import com.hams.medical.dto.Patient;
import com.hams.medical.feignclient.PatientClient;
import com.hams.medical.model.MedicalHistory;
import com.hams.medical.repository.MedicalHistoryRepository;
import com.hams.medical.service.MedicalHistoryServiceImpl;

@SpringBootTest
class MedicalHistoryServiceTests {

    @Mock
    private MedicalHistoryRepository repository;

    @Mock
    private PatientClient patientClient;

    @InjectMocks
    private MedicalHistoryServiceImpl service;

    private MedicalHistory medicalHistory;
    private Patient patient;

    @BeforeEach
    void setUp() {
        medicalHistory = new MedicalHistory();
        medicalHistory.setPatientId(1L);
        medicalHistory.setDiagnosis("Test Diagnosis");
        medicalHistory.setTreatment("Test Treatment");
        
        patient = new Patient();
        patient.setId(1L);
        patient.setName("John Doe");
    }

    @Test
    void testGetMedicalHistoryWithPatient() {
        when(patientClient.getPatientById(1L)).thenReturn(patient);
        when(repository.findByPatientId(1L)).thenReturn(Arrays.asList(medicalHistory));

        MedicalHistoryPatientResponseDTO response = service.getMedicalHistoryWithPatient(1L);

        assertNotNull(response);
        assertEquals(1L, response.getPatient().getId());
        assertFalse(response.getMedicalHistory().isEmpty());
    }

    @Test
    void testSaveMedicalHistory() {
        when(patientClient.getPatientById(1L)).thenReturn(patient);
        when(repository.save(any(MedicalHistory.class))).thenReturn(medicalHistory);

        String response = service.saveHistory(medicalHistory);
        assertEquals("Medical history saved successfully.", response);
    }

    @Test
    void testUpdateMedicalHistory() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(medicalHistory));
        when(repository.save(any(MedicalHistory.class))).thenReturn(medicalHistory);

        String response = service.updateHistory(1L, medicalHistory);
        assertEquals("Medical history updated successfully.", response);
    }

    @Test
    void testGetMedicalHistoryById() {
        when(repository.findById(1L)).thenReturn(Optional.of(medicalHistory));

        MedicalHistory response = service.getById(1L);
        assertNotNull(response);
        assertEquals(1L, response.getPatientId());
    }

    @Test
    void testDeleteMedicalHistoryById() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        String response = service.deleteById(1L);
        assertEquals("Medical history deleted successfully.", response);
        verify(repository, times(1)).deleteById(1L);
    }
}
