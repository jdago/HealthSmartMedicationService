package com.example.medicationservice.web;

import com.example.medicationservice.domain.Medication;
import com.example.medicationservice.domain.MedicationService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("medications")
public class MedicationController {
    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping("{patientId}")
    public Iterable<Medication> findAllForPatient(@PathVariable String patientId) {
        return medicationService.findAllForPatient(patientId);
    }

    @GetMapping("{patientId}/{medicationName}")
    public Optional<Medication> findByMedicationName(@PathVariable String patientId, @PathVariable String medicationName) {
        return medicationService.findByMedicationName(patientId, medicationName);
    }

    @GetMapping("{patientId}/{medicationNDC}")
    public Optional<Medication> findByMedicationNDC(@PathVariable String patientId, @PathVariable String medicationNDC) {
        return medicationService.findByMedicationNDC(patientId, medicationNDC);
    }

    @GetMapping("{patientId}/history")
    public Iterable<Medication> findMedicationHistory(@PathVariable String patientId) {
        return medicationService.findMedicationHistory(patientId);
    }

    @GetMapping("/label/{medicationName}/{dosageInMilligrams}")
    public Optional<Medication> findLabelByMedicationNameAndDoseSize(@PathVariable String medicationName, @PathVariable int dosageInMilligrams) {
        return medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams);
    }

}
