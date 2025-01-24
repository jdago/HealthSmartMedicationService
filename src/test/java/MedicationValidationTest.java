import com.example.medicationservice.MedicationService;
import com.example.medicationservice.MedicationsNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result
        .MockMvcResultMatchers.status;

@WebMvcTest(MedicationController.class)
public class MedicationValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MedicationService medicationService;

    @InjectMocks
    private MedicationController medicationController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(medicationController).build();
    }
    @Test
    void whenGetMedicationListPatientIdNotFoundReturn404() throws Exception {
        String patientId = "XAB-1214";

        given(medicationService.findAllForPatient(patientId))
        .willThrow(PatientNotFoundException.class);

        mockMvc.perform(get("/medications/{patientId}", patientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetMedicationListNoExistingMedicationsReturn404() throws Exception {
        String patientId = "XAB-1215";

        given(medicationService.findAllForPatient(patientId))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(get("/medications/{patientId}",patientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetMedicationListPatientIdNotFormattedProperlyReturn400() throws Exception {
        String patientId = "XB-12905";

        given(medicationService.findAllForPatient(patientId))
                .willThrow(InvalidPatientIdException.class);

        mockMvc.perform(get("/medications/{patientId}", patientId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetMedicationNamePatientIdNotFormattedProperlyReturn400() throws Exception {
        String patientId = "XB-12905";
        String medicationName = "Oxycodone";

        given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(InvalidPatientIdException.class);

        mockMvc.perform(get("/medications/{patientId}/{medicationName}", patientId, medicationName))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetMedicationNameNotFoundThenShouldReturn404() throws Exception {
        String patientId = "XAB-1289";
        String medicationName = "Codeine";

        given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(get("/medications/{patientId}/{medicationName}", patientId, medicationName))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetMedicationNameNoMedicationFoundMedicationsReturn404() throws Exception {
        String patientId = "XAB-1215";
        String medicationName = "Methadone";

        given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(get("/medications/{patientId}/{medicationName}",patientId,medicationName))
                .andExpect(status().isNotFound());
    }
    @Test
    void whenGetMedicationPatientIDNotFoundThenShouldReturn404() throws Exception {
        String patientId = "XAB-1290";
        String medicationName= "Oxycodone";

        given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(PatientNotFoundException.class);

        mockMvc.perform(get ("/medications/{patientId}/{medicationName}", patientId, medicationName))
                .andExpect(status().isNotFound());

    }
    @Test
    void whenGetMedicationNameImproperFormatThenShouldReturn400() throws Exception {
        String patientId = "XAB-1290";
        String medicationName = "";

        given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(InvalidMedicationNameException.class);

        mockMvc.perform(get ("/medications/{patientId}/{medicationName}", patientId, medicationName))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetMedicationFromNDCNoMedicationFoundThenShouldReturn404() throws Exception {
        String patientId = "XAB-1290";
        String medicationNDC = "4120-8901-12";

        given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(get("/medications/{patientId}/{medicationNDC}",patientId, medicationNDC))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetMedicationFromNDCPatientIdNotFormattedProperlyReturn400() throws Exception {
        String patientId = "XB-12905";
        String medicationNDC = "4120-8901-12";

        given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(InvalidPatientIdException.class);

        mockMvc.perform(get("/medications/{patientId}/{medicationNDC}", patientId, medicationNDC))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetMedicationFromNDCNotExistingThenShouldReturn404() throws Exception {
        String patientId = "XAB-1289";
        String medicationNDC = "4120-8901-12";

        given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(get("/medications/{patientId}/{medicationNDC}", patientId, medicationNDC))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetMedicationFromNDCNoExistingNDCShouldReturn404() throws Exception {
        String patientId = "XAB-1215";
        String medicationNDC = "4120-8901-10";
        //Fix it should be that no medications found for the NDC
        given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(MedicationNDCNotFoundException.class);

        mockMvc.perform(get("/medications/{patientId}/{medicationNDC}",patientId,medicationNDC))
                .andExpect(status().isNotFound());
    }
    @Test
    void whenGetMedicationFromNDCPatientIDNotExistingThenShouldReturn404() throws Exception {
        String patientId = "XAB-1282";
        String medicationNDC= "4120-8901-12";

        given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(PatientNotFoundException.class);

        mockMvc.perform(get ("/medications/{patientId}/{medicationNDC}", patientId, medicationNDC))
                .andExpect(status().isNotFound());

    }

    @Test
    void whenGetMedicationFromNDCMedicationNDCNotProperlyFormattedThenShouldReturn400() throws Exception {
        String patientId = "XAB-1289";
        String medicationNDC = "4120-390-1";

        given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(InvalidNDCException.class);

        mockMvc.perform(get("/medications/{patientId}/{medicationNDC}", patientId, medicationNDC))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetMedicationHistoryPatientIDNotFoundThenReturn404() throws Exception {
        String patientId = "XAB-1223";

        given(medicationService.findMedicationHistory(patientId))
                .willThrow(PatientNotFoundException.class);

        mockMvc.perform(get("/medications/{patientId}/history", patientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetMedicationHistoryPatientIDImproperlyFormattedThenReturn400() throws Exception {
        String patientId = "XA-12904";

        given(medicationService.findMedicationHistory(patientId))
                .willThrow(InvalidPatientIdException.class);

        mockMvc.perform(get("/medications/{patientId}/history", patientId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetMedicationHistoryNoMedicationsFoundThenReturn404() throws Exception {
        String patientId = "XAB-1224";

        given(medicationService.findMedicationHistory(patientId))
                .willThrow(MedicationHistoryDoesNotExistException.class);

        mockMvc.perform(get("/medications/{patientId}/history", patientId))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenFindLabelByMedicationNameAndDosageImproperFormatOfMedicationThenReturn400() throws Exception {
        String medicationName = "";
        int dosageInMilligrams = 30;

        given(medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams))
                .willThrow(InvalidMedicationNameException.class);

        mockMvc.perform(get("/medications/label/{medicationName}/{dosage}", medicationName, dosageInMilligrams))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindLabelByMedicationNameAndDosageMedicationNotExistThenReturn404() throws Exception {
        String medicationName = "prednisone";
        int dosageInMilligrams = 55;

        given(medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(get("/medications/label/{medicationName}/{dosage}", medicationName, dosageInMilligrams))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenFindLabelByMedicationNameAndDosageImproperFormatOfDosageThenReturn400() throws Exception {
        String medicationName = "fioricet";
        int dosageInMilligrams= -1;

        given(medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams))
                .willThrow(InvalidDosageRangeException.class);

        mockMvc.perform(get("/medications/label/{medicationName}/{dosage}", medicationName, dosageInMilligrams))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindLabelByMedicationNameAndDosageNoLabelExistsThenReturn404() throws Exception {
        String medicationName = "gabapentin";
        int dosageInMilligrams = 10;

        given(medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams))
                .willThrow(LabelNotFoundException.class);

        mockMvc.perform(get("/medications/label/{medicationName}/{dosage}", medicationName, dosageInMilligrams))
                .andExpect(status().isNotFound());
    }
}

