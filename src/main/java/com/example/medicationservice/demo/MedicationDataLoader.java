package com.example.medicationservice.demo;

import com.example.medicationservice.domain.Medication;
import com.example.medicationservice.persistence.MedicationRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Profile("testdata")
public class MedicationDataLoader {

    private final MedicationRepository medicationRepository;

    public MedicationDataLoader(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadMedicationTestData() {
        medicationRepository.deleteAll();
        var medication1 = Medication.of(
                0, "Aspirin", "1234-5678-01", LocalDate.of(2025, 12, 31), 100, 5, "Non-Controlled",
                "Painkiller", "Active", "XAB-1001");

        var medication2 = Medication.of(
                0, "Ibuprofen", "2345-6789-02", LocalDate.of(2026, 5, 15), 200, 3, "Non-Controlled",
                "Painkiller", "Active", "XAB-1002");

        var medication3 = Medication.of(
                0, "Oxycodone", "3456-7890-03", LocalDate.of(2027, 3, 20), 50, 2, "Controlled",
                "Opioid", "Active", "XAB-1003");

        medicationRepository.saveAll(List.of(medication1, medication2, medication3));
    }
}
