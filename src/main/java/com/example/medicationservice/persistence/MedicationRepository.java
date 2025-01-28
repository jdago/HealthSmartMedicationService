package com.example.medicationservice.persistence;

import com.example.medicationservice.domain.Medication;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MedicationRepository extends CrudRepository<Medication,Long>{
    @Query("SELECT * from medication where patientid = :patientId AND status = 'Active'")
    Iterable<Medication> findAllForPatient(String patientId);
    @Query("SELECT medicationname from medication where patientid = :patientId AND medicationname = :medicationName")
    Optional<Medication> findByMedicationName(String patientId, String medicationName);
    @Query("SELECT medicationname from medication where patientid = :patientId AND medicationndc = :medicationndc")
    Optional<Medication> findByMedicationNDC(String patientId, String medicationName);
    @Query("SELECT * from medication where patientid = :patientId")
    Iterable<Medication> findMedicationHistory(String patientId);
    @Query("SELECT * from medication where medicationname = :medicationName AND dosageinmilligrams = :dosageInMilligrams")
    Optional<Medication> findLabelByMedicationNameAndDoseSize(String medicationName, int dosageInMilligrams);
}
