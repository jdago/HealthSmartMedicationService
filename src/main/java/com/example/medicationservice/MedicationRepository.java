package com.example.medicationservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface MedicationRepository extends CrudRepository<Medication,Long>{
    Iterable<Medication> findAllForPatient(String patientId);
    Optional<Medication> findByMedicationName(String patientId, String medicationName);
    Optional<Medication> findByMedicationNDC(String patientId, String medicationName);
    Iterable<Medication> findMedicationHistory(String patientId);
    Iterable<Medication> findLabelByMedicationNameAndDoseSize(String medicationName, int dosageInMilligrams);
}
