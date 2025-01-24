import com.example.medicationservice.MedicationService;
import com.example.medicationservice.Medication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("medications")
public class MedicationController {
    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping("/medications")
    public Iterable<Medication> findAllForPatient(@PathVariable String patientId) {
        return medicationService.findAllForPatient(patientId);
    }

    @GetMapping("/{medicationName}")
    public Medication findByName(@PathVariable String medicationName) {
        return medicationService.findByName(medicationName);
    }

    @GetMapping("/{patientId}")
    public Optional<Medication> findByPatientId(@PathVariable String patientId) {
        return medicationService.findByPatientId(patientId);
    }

    @GetMapping("/history/{patientId}")
    public Iterable<Medication> findHistoryById(@PathVariable String patientId) {
        return medicationService.findHistoryById(patientId);
    }

    @GetMapping("/label/{medicationName}/{dosage}")
    public Iterable<Medication> findLabelByNameAndDosage(@PathVariable String medicationName, @PathVariable int dosage) {
        return medicationService.findLabelByNameAndDosage(medicationName, dosage);
    }

}
