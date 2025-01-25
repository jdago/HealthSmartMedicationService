package com.example.medicationservice.web;
import com.example.medicationservice.domain.MedicationService;

import com.example.medicationservice.domain.MedicationsNotFoundException;
import com.example.medicationservice.domain.PatientNotFoundException;
import com.example.medicationservice.domain.InvalidDosageRangeException;
import com.example.medicationservice.domain.InvalidMedicationNameException;
import com.example.medicationservice.domain.LabelNotFoundException;
import com.example.medicationservice.domain.MedicationHistoryDoesNotExistException;
import com.example.medicationservice.domain.MedicationNDCNotFoundException;
import com.example.medicationservice.domain.InvalidPatientIdException;
import com.example.medicationservice.domain.InvalidNDCException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(MedicationController.class)
class MedicationControllerTest {

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

        BDDMockito.given(medicationService.findAllForPatient(patientId))
        .willThrow(PatientNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}", patientId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenGetMedicationListNoExistingMedicationsReturn404() throws Exception {
        String patientId = "XAB-1215";

        BDDMockito.given(medicationService.findAllForPatient(patientId))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}",patientId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenGetMedicationListPatientIdNotFormattedProperlyReturn400() throws Exception {
        String patientId = "XB-12905";

        BDDMockito.given(medicationService.findAllForPatient(patientId))
                .willThrow(InvalidPatientIdException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}", patientId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenGetMedicationNamePatientIdNotFormattedProperlyReturn400() throws Exception {
        String patientId = "XB-12905";
        String medicationName = "Oxycodone";

        BDDMockito.given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(InvalidPatientIdException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationName}", patientId, medicationName))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenGetMedicationNameNotFoundThenShouldReturn404() throws Exception {
        String patientId = "XAB-1289";
        String medicationName = "Codeine";

        BDDMockito.given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationName}", patientId, medicationName))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenGetMedicationNameNoMedicationFoundMedicationsReturn404() throws Exception {
        String patientId = "XAB-1215";
        String medicationName = "Methadone";

        BDDMockito.given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationName}",patientId,medicationName))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void whenGetMedicationPatientIDNotFoundThenShouldReturn404() throws Exception {
        String patientId = "XAB-1290";
        String medicationName= "Oxycodone";

        BDDMockito.given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(PatientNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get ("/medications/{patientId}/{medicationName}", patientId, medicationName))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
    @Test
    void whenGetMedicationNameImproperFormatThenShouldReturn400() throws Exception {
        String patientId = "XAB-1290";
        String medicationName = "";

        BDDMockito.given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(InvalidMedicationNameException.class);

        mockMvc.perform(MockMvcRequestBuilders.get ("/medications/{patientId}/{medicationName}", patientId, medicationName))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenGetMedicationFromNDCNoMedicationFoundThenShouldReturn404() throws Exception {
        String patientId = "XAB-1290";
        String medicationNDC = "4120-8901-12";

        BDDMockito.given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationNDC}",patientId, medicationNDC))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenGetMedicationFromNDCPatientIdNotFormattedProperlyReturn400() throws Exception {
        String patientId = "XB-12905";
        String medicationNDC = "4120-8901-12";

        BDDMockito.given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(InvalidPatientIdException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationNDC}", patientId, medicationNDC))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenGetMedicationFromNDCNotExistingThenShouldReturn404() throws Exception {
        String patientId = "XAB-1289";
        String medicationNDC = "4120-8901-12";

        BDDMockito.given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationNDC}", patientId, medicationNDC))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenGetMedicationFromNDCNoExistingNDCShouldReturn404() throws Exception {
        String patientId = "XAB-1215";
        String medicationNDC = "4120-8901-10";
        //Fix it should be that no medications found for the NDC
        BDDMockito.given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(MedicationNDCNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationNDC}",patientId,medicationNDC))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void whenGetMedicationFromNDCPatientIDNotExistingThenShouldReturn404() throws Exception {
        String patientId = "XAB-1282";
        String medicationNDC= "4120-8901-12";

        BDDMockito.given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(PatientNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get ("/medications/{patientId}/{medicationNDC}", patientId, medicationNDC))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void whenGetMedicationFromNDCMedicationNDCNotProperlyFormattedThenShouldReturn400() throws Exception {
        String patientId = "XAB-1289";
        String medicationNDC = "4120-390-1";

        BDDMockito.given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(InvalidNDCException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationNDC}", patientId, medicationNDC))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenGetMedicationHistoryPatientIDNotFoundThenReturn404() throws Exception {
        String patientId = "XAB-1223";

        BDDMockito.given(medicationService.findMedicationHistory(patientId))
                .willThrow(PatientNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/history", patientId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenGetMedicationHistoryPatientIDImproperlyFormattedThenReturn400() throws Exception {
        String patientId = "XA-12904";

        BDDMockito.given(medicationService.findMedicationHistory(patientId))
                .willThrow(InvalidPatientIdException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/history", patientId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenGetMedicationHistoryNoMedicationsFoundThenReturn404() throws Exception {
        String patientId = "XAB-1224";

        BDDMockito.given(medicationService.findMedicationHistory(patientId))
                .willThrow(MedicationHistoryDoesNotExistException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/history", patientId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenFindLabelByMedicationNameAndDosageImproperFormatOfMedicationThenReturn400() throws Exception {
        String medicationName = "";
        int dosageInMilligrams = 30;

        BDDMockito.given(medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams))
                .willThrow(InvalidMedicationNameException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/label/{medicationName}/{dosage}", medicationName, dosageInMilligrams))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenFindLabelByMedicationNameAndDosageMedicationNotExistThenReturn404() throws Exception {
        String medicationName = "prednisone";
        int dosageInMilligrams = 55;

        BDDMockito.given(medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/label/{medicationName}/{dosage}", medicationName, dosageInMilligrams))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenFindLabelByMedicationNameAndDosageImproperFormatOfDosageThenReturn400() throws Exception {
        String medicationName = "fioricet";
        int dosageInMilligrams= -1;

        BDDMockito.given(medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams))
                .willThrow(InvalidDosageRangeException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/label/{medicationName}/{dosage}", medicationName, dosageInMilligrams))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenFindLabelByMedicationNameAndDosageNoLabelExistsThenReturn404() throws Exception {
        String medicationName = "gabapentin";
        int dosageInMilligrams = 10;

        BDDMockito.given(medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams))
                .willThrow(LabelNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/label/{medicationName}/{dosage}", medicationName, dosageInMilligrams))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

