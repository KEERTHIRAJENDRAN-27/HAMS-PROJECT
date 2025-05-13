package com.hams.medical.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hams.medical.dto.MedicalHistoryDTO;
import com.hams.medical.dto.MedicalHistoryPatientResponseDTO;
import com.hams.medical.model.MedicalHistory;
import com.hams.medical.service.MedicalHistoryService;

@RestController
@RequestMapping("/medicalHistory")
public class MedicalHistoryController {

	@Autowired
	private MedicalHistoryService service;

	@PostMapping("/save")
	public String save(@RequestBody MedicalHistoryDTO dto) {
		return service.saveHistory(toEntity(dto));
	}

	@PutMapping("/update/{id}")
	public String update(@PathVariable Long id, @RequestBody MedicalHistoryDTO dto) {
		return service.updateHistory(id, toEntity(dto));
	}

	@GetMapping("/get/{id}")
	public MedicalHistoryDTO getById(@PathVariable Long id) {
		return toDTO(service.getById(id));
	}

	@GetMapping("/patient/{patientId}")
	public List<MedicalHistoryDTO> getByPatient(@PathVariable Long patientId) {
		return service.getByPatientId(patientId).stream().map(this::toDTO).collect(Collectors.toList());
	}

	@GetMapping("/all")
	public List<MedicalHistoryDTO> getAll() {
		return service.getAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		return service.deleteById(id);
	}

	@GetMapping("/patienthistory/{patientId}")
	public MedicalHistoryPatientResponseDTO getHistoryWithPatient(@PathVariable Long patientId) {
		return service.getMedicalHistoryWithPatient(patientId);
	}

	private MedicalHistoryDTO toDTO(MedicalHistory mh) {
		MedicalHistoryDTO dto = new MedicalHistoryDTO();
		dto.setHistoryId(mh.getHistoryId());
		dto.setPatientId(mh.getPatientId());
		dto.setDiagnosis(mh.getDiagnosis());
		dto.setTreatment(mh.getTreatment());
		dto.setDateOfVisit(mh.getDateOfVisit());
		return dto;
	}

	private MedicalHistory toEntity(MedicalHistoryDTO dto) {
		MedicalHistory mh = new MedicalHistory();
		mh.setHistoryId(dto.getHistoryId());
		mh.setPatientId(dto.getPatientId());
		mh.setDiagnosis(dto.getDiagnosis());
		mh.setTreatment(dto.getTreatment());
		mh.setDateOfVisit(dto.getDateOfVisit());
		return mh;
	}
}
