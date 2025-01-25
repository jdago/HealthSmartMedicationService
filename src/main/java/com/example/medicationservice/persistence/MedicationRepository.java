package com.example.medicationservice.persistence;
import com.example.medicationservice.domain.Medication;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface MedicationRepository extends CrudRepository<Medication,Long>{
    @Query("SELECT * from Medication where patientId = :patientId AND status = 'current'")
    Iterable<Medication> findAllForPatient(String patientId);
    @Query("SELECT medicationName from Medication where patientId = :patientId AND medicationName = :medicationName")
    Optional<Medication> findByMedicationName(String patientId, String medicationName);
    @Query("SELECT medicationName from Medication where patientId = :patientId AND medicationName = :medicationName")
    Optional<Medication> findByMedicationNDC(String patientId, String medicationName);
    @Query("SELECT * from Medication where patientId = :patientId")
    Iterable<Medication> findMedicationHistory(String patientId);
    @Query("SELECT medicationName from Medication where medicationName = :medicationName AND dosageInMilligrams = :dosageInMilligrams")
    Iterable<Medication> findLabelByMedicationNameAndDoseSize(String medicationName, int dosageInMilligrams);
}
