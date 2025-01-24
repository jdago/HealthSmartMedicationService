package com.example.medicationservice;

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
    public Medication findByName(String medicationName){
        return medicationRepository.findByName(medicationName).orElseThrow(()-> new MedicationsNotFoundException(medicationName));
    }

    public Optional<Medication> findByPatientId(String patientId) {
        return medicationRepository.findByPatientId(patientId);
    }

    public Iterable<Medication> findHistoryById(String patientId){

        return medicationRepository.findHistory(patientId);
    }

    public Iterable<Medication> findLabelByNameAndDosage(String medicationName, int dosage){
        return medicationRepository.findLabelByNameAndDosage(medicationName, dosage);
    }
}


