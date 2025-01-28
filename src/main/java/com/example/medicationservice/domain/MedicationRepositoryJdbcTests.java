package com.example.medicationservice.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.medicationservice.persistence.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
public class MedicationRepositoryJdbcTests {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MedicationRepository medicationRepository;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void findAllMedicationsForPatientWhenExists() {
        var patientId = "XAB-1001";
        var medication1 = Medication.of(
                0, "Aspirin", "1234-5678-01", LocalDate.of(2025, 12, 31), 100, 5, "Non-Controlled",
                "Painkiller", "Active", "XAB-1001");
        var medication2 = Medication.of(
                0, "Ibuprofen", "2345-6789-02", LocalDate.of(2024, 5, 15), 200, 3, "Non-Controlled",
                "Painkiller", "Expired", "XAB-1001");
        jdbcAggregateTemplate.insert(medication1);
        jdbcAggregateTemplate.insert(medication2);

        Iterable<Medication> actualMedication = medicationRepository.findAllForPatient(patientId);
        List<Medication> medicationList = new ArrayList<>();
        actualMedication.forEach(medicationList::add);

        assertThat(medicationList).hasSize(1);
    }

    @Test
    void findByMedicationNameWhenExists(){
        var patientId = "XAB-1001";
        var medication1 = Medication.of(
                0, "Aspirin", "1234-5678-01", LocalDate.of(2025, 12, 31), 100, 5, "Non-Controlled",
                "Painkiller", "Active", "XAB-1001");
        var medicationName = medication1.medicationName();

        jdbcAggregateTemplate.insert(medication1);

        Optional<Medication> actualMedication = medicationRepository.findByMedicationName(patientId, medicationName);

        assertThat(actualMedication).isPresent();
        assertThat(actualMedication.get().patientId()).isEqualTo(medication1.patientId());
        assertThat(actualMedication.get().medicationName()).isEqualTo(medication1.medicationName());
    }

    @Test
    void findByMedicationNDCWhenExists() {
        var patientId = "XAB-1001";
        var medication1 = Medication.of(
                0, "Aspirin", "1234-5678-01", LocalDate.of(2025, 12, 31), 100, 5, "Non-Controlled",
                "Painkiller", "Active", "XAB-1001");
        var medicationNDC = medication1.medicationNDC();

        jdbcAggregateTemplate.insert(medication1);

        Optional<Medication> actualMedication = medicationRepository.findByMedicationNDC(patientId, medicationNDC);

        assertThat(actualMedication).isPresent();
        assertThat(actualMedication.get().patientId()).isEqualTo(medication1.patientId());
        assertThat(actualMedication.get().medicationNDC()).isEqualTo(medication1.medicationNDC());
    }

    @Test
    void findMedicationHistoryWhenExists() {
        var patientId = "XAB-1001";
        var medication1 = Medication.of(
                0, "Aspirin", "1234-5678-01", LocalDate.of(2025, 12, 31), 100, 5, "Non-Controlled",
                "Painkiller", "Active", "XAB-1001");
        var medication2 = Medication.of(
                0, "Ibuprofen", "2345-6789-02", LocalDate.of(2024, 5, 15), 200, 3, "Non-Controlled",
                "Painkiller", "Expired", "XAB-1001");

        jdbcAggregateTemplate.insert(medication1);
        jdbcAggregateTemplate.insert(medication2);

        Iterable<Medication> actualMedication = medicationRepository.findAllForPatient(patientId);
        List<Medication> medicationList = new ArrayList<>();
        actualMedication.forEach(medicationList::add);

        assertThat(medicationList).isNotEmpty();

        Optional<Medication> firstMedicationResult = medicationList.stream()
                .filter(med -> med.medicationName().equals(medication1.medicationName())
                        && med.status().equals(medication1.medicationName())
                        && med.patientId().equals(medication1.patientId()))
                .findFirst();
        assertThat(firstMedicationResult).isPresent();

        Optional<Medication> secondMedicationResult = medicationList.stream()
                .filter(med -> med.medicationName().equals(medication2.medicationName())
                        && med.status().equals(medication2.status())
                        && med.patientId().equals(medication2.patientId()))
                .findFirst();
        assertThat(secondMedicationResult).isPresent();
    }

    @Test
    void findMedicationLabelWhenMedicationWithNameAndDosageExists() {
        var medication1 = Medication.of(
                0, "Aspirin", "1234-5678-01", LocalDate.of(2025, 12, 31), 100, 5, "Non-Controlled",
                "Painkiller", "Active", "XAB-1001");
        var medicationName = medication1.medicationName();
        var dosage = medication1.dosageInMilligrams();

        jdbcAggregateTemplate.insert(medication1);

        Optional<Medication> actualMedication = medicationRepository.findLabelByMedicationNameAndDoseSize(medicationName, dosage);

        assertThat(actualMedication).isPresent();
        assertThat(actualMedication.get().medicationName()).isEqualTo(medication1.medicationClass());
        assertThat(actualMedication.get().dosageInMilligrams()).isEqualTo(medication1.dosageInMilligrams());

    }




    }
