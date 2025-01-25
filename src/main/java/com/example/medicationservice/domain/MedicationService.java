package com.example.medicationservice.domain;
import com.example.medicationservice.persistence.MedicationRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;

    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public Iterable<Medication> findAllForPatient(String patientId){
        return medicationRepository.findAllForPatient(patientId);
    }
    public Optional<Medication> findByMedicationName(String patientId, String medicationName){
        return medicationRepository.findByMedicationName(patientId, medicationName);
    }
    public Optional<Medication> findByMedicationNDC(String patientId, String medicationNDC) {
        return medicationRepository.findByMedicationNDC(patientId, medicationNDC);
    }

    public Iterable<Medication> findMedicationHistory(String patientId){
        return medicationRepository.findMedicationHistory(patientId);
    }

    public Iterable<Medication> findLabelByMedicationNameAndDoseSize(String medicationName, int dosageInMilligrams){
        return medicationRepository.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams);
    }
}


