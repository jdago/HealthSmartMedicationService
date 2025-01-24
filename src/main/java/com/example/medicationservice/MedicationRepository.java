package com.example.medicationservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface MedicationRepository extends CrudRepository<Medication,Long>{
    Iterable<Medication> findAllForPatient(String patientId);
    Optional<Medication> findByName(String medicationName);
    Optional<Medication> findByPatientId(String patientId);
    Iterable<Medication> findHistory(String patientId);
    Iterable<Medication> findLabelByNameAndDosage(String medicationName, int dosage);
}
