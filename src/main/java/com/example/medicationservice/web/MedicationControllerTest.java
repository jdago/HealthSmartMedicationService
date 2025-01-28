package com.example.medicationservice.web;
import com.example.medicationservice.demo.MedicationDataLoader;
import com.example.medicationservice.domain.*;
import com.example.medicationservice.persistence.MedicationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;


@WebMvcTest(MedicationController.class)
@Import(MedicationDataLoader.class)
@ActiveProfiles("testdata")
class MedicationControllerTest {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MockMvc mockMvc;

    @Mock
    private MedicationService medicationService;

    @InjectMocks
    private MedicationController medicationController;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private MedicationRepository medicationRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(medicationController).build();
    }

    @Test
    void whenGetMedicationListPatientAndMedicationsExistReturn200() throws Exception {
        String patientId = "XAB-1215";

        Iterable<Medication> medicationsIterable = medicationRepository.findAllForPatient(patientId);
        List<Medication> medications = new ArrayList<>();
        medicationsIterable.forEach(medications::add);

        BDDMockito.given(medicationService.findAllForPatient(patientId)).willReturn(medications);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}", patientId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-list", responseFields(
                        fieldWithPath("[].name").description("Name of the medication"),
                        fieldWithPath("[].NDC").description("National Drug Code of the medication"),
                        fieldWithPath("[].expirationDate").description("Expiration date of the medication"),
                        fieldWithPath("[].dosageInMilligrams").description("Prescribed dosage (in milligrams) of the medication"),
                        fieldWithPath("[].availableRefills").description("Available refills remaining for the medication"),
                        fieldWithPath("[].controlledStatus").description("Controlled status of the medication"),
                        fieldWithPath("[].medicationClass").description("Status (i.e., current, expired) of the medication")
                )))
                .andReturn();
    }

    @Test
    void whenGetMedicationListPatientIdNotFoundReturn404() throws Exception {
        String patientId = "XAB-1214";

        BDDMockito.given(medicationService.findAllForPatient(patientId))
        .willThrow(PatientNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}", patientId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andDo(MockMvcResultHandlers.print())
                        .andDo(document("medication-list-patient-not-found"))
                        .andReturn();
    }

    @Test
    void whenGetMedicationListNoExistingMedicationsReturn404() throws Exception {
        String patientId = "XAB-1215";

        BDDMockito.given(medicationService.findAllForPatient(patientId))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}",patientId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-list-medications-do-not-exist"))
                        .andReturn();
    }

    @Test
    void whenGetMedicationListPatientIdNotFormattedProperlyReturn400() throws Exception {
        String patientId = "XB-12905";

        BDDMockito.given(medicationService.findAllForPatient(patientId))
                .willThrow(InvalidPatientIdException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}", patientId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-list-improper-patientid-format"))
                .andReturn();
    }

    @Test
    void whenGetMedicationNamePatientIdNotFormattedProperlyReturn400() throws Exception {
        String patientId = "XB-12905";
        String medicationName = "Oxycodone";

        BDDMockito.given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(InvalidPatientIdException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationName}", patientId, medicationName))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-name-improper-patientid-format"))
                .andReturn();
    }

    @Test
    void whenGetMedicationNameNotFoundThenShouldReturn404() throws Exception {
        String patientId = "XAB-1289";
        String medicationName = "Codeine";

        BDDMockito.given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationName}", patientId, medicationName))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-name-not-found"))
                .andReturn();
    }

    @Test
    void whenGetMedicationNameNoExistingMedicationNameMedicationsReturn404() throws Exception {
        String patientId = "XAB-1215";
        String medicationName = "Methadone";

        BDDMockito.given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(MedicationDoesNotExistException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationName}",patientId,medicationName))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-name-no-existing-medication"))
                .andReturn();
    }
    @Test
    void whenGetMedicationPatientIDNotFoundThenShouldReturn404() throws Exception {
        String patientId = "XAB-1290";
        String medicationName= "Oxycodone";

        BDDMockito.given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(PatientNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get ("/medications/{patientId}/{medicationName}", patientId, medicationName))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-name-no-patient-found"))
                .andReturn();

    }
    @Test
    void whenGetMedicationNameImproperFormatThenShouldReturn400() throws Exception {
        String patientId = "XAB-1290";
        String medicationName = "";

        BDDMockito.given(medicationService.findByMedicationName(patientId, medicationName))
                .willThrow(InvalidMedicationNameException.class);

        mockMvc.perform(MockMvcRequestBuilders.get ("/medications/{patientId}/{medicationName}", patientId, medicationName))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-name-improper-format"))
                .andReturn();
    }

    @Test
    void whenGetMedicationFromNDCNoMedicationFoundThenShouldReturn404() throws Exception {
        String patientId = "XAB-1290";
        String medicationNDC = "4120-8901-12";

        BDDMockito.given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationNDC}",patientId, medicationNDC))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-ndc-no-medication-found"))
                .andReturn();
    }

    @Test
    void whenGetMedicationFromNDCPatientIdNotFormattedProperlyReturn400() throws Exception {
        String patientId = "XB-12905";
        String medicationNDC = "4120-8901-12";

        BDDMockito.given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(InvalidPatientIdException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationNDC}", patientId, medicationNDC))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-ndc-invalid-patientid-format"))
                .andReturn();
    }

    @Test
    void whenGetMedicationFromNDCNotExistingThenShouldReturn404() throws Exception {
        String patientId = "XAB-1289";
        String medicationNDC = "4120-8901-12";

        BDDMockito.given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationNDC}", patientId, medicationNDC))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-ndc-medication-not-exist"))
                .andReturn();
    }

    @Test
    void whenGetMedicationFromNDCNoExistingNDCShouldReturn404() throws Exception {
        String patientId = "XAB-1215";
        String medicationNDC = "4120-8901-10";
        //Fix it should be that no medications found for the NDC
        BDDMockito.given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(MedicationNDCNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationNDC}",patientId,medicationNDC))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(document("medication-ndc-no-existing-mdc"))
                .andReturn();
    }
    @Test
    void whenGetMedicationFromNDCPatientIDNotExistingThenShouldReturn404() throws Exception {
        String patientId = "XAB-1282";
        String medicationNDC= "4120-8901-12";

        BDDMockito.given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(PatientNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get ("/medications/{patientId}/{medicationNDC}", patientId, medicationNDC))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(document("medication-ndc-no-existing-patientid"))
                .andReturn();

    }

    @Test
    void whenGetMedicationFromNDCMedicationNDCNotProperlyFormattedThenShouldReturn400() throws Exception {
        String patientId = "XAB-1289";
        String medicationNDC = "4120-390-1";

        BDDMockito.given(medicationService.findByMedicationNDC(patientId, medicationNDC))
                .willThrow(InvalidNDCException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/{medicationNDC}", patientId, medicationNDC))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-ndc-improper-ndc-format"))
                .andReturn();
    }

    @Test
    void whenGetMedicationHistoryPatientIDNotFoundThenReturn404() throws Exception {
        String patientId = "XAB-1223";

        BDDMockito.given(medicationService.findMedicationHistory(patientId))
                .willThrow(PatientNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/history", patientId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-history-patientid-not-found"))
                .andReturn();
    }

    @Test
    void whenGetMedicationHistoryPatientIDImproperlyFormattedThenReturn400() throws Exception {
        String patientId = "XA-12904";

        BDDMockito.given(medicationService.findMedicationHistory(patientId))
                .willThrow(InvalidPatientIdException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/history", patientId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-history-invalid-patientid-format"))
                .andReturn();
    }

    @Test
    void whenGetMedicationHistoryNoMedicationsFoundThenReturn404() throws Exception {
        String patientId = "XAB-1224";

        BDDMockito.given(medicationService.findMedicationHistory(patientId))
                .willThrow(MedicationHistoryDoesNotExistException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{patientId}/history", patientId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-history-no-medications-found"))
                .andReturn();
    }

    @Test
    void whenFindLabelByMedicationNameAndDosageImproperFormatOfMedicationThenReturn400() throws Exception {
        String medicationName = "";
        int dosageInMilligrams = 30;

        BDDMockito.given(medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams))
                .willThrow(InvalidMedicationNameException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/label/{medicationName}/{dosage}", medicationName, dosageInMilligrams))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-label-improper-medication-format"))
                .andReturn();
    }

    @Test
    void whenFindLabelByMedicationNameAndDosageMedicationNotExistThenReturn404() throws Exception {
        String medicationName = "prednisone";
        int dosageInMilligrams = 55;

        BDDMockito.given(medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams))
                .willThrow(MedicationsNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/label/{medicationName}/{dosage}", medicationName, dosageInMilligrams))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-label-no-existing-medication"))
                .andReturn();
    }

    @Test
    void whenFindLabelByMedicationNameAndDosageImproperFormatOfDosageThenReturn400() throws Exception {
        String medicationName = "fioricet";
        int dosageInMilligrams= -1;

        BDDMockito.given(medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams))
                .willThrow(InvalidDosageRangeException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/label/{medicationName}/{dosage}", medicationName, dosageInMilligrams))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-label-improper-dosage-format"))
                .andReturn();
    }

    @Test
    void whenFindLabelByMedicationNameAndDosageNoLabelExistsThenReturn404() throws Exception {
        String medicationName = "gabapentin";
        int dosageInMilligrams = 10;

        BDDMockito.given(medicationService.findLabelByMedicationNameAndDoseSize(medicationName, dosageInMilligrams))
                .willThrow(LabelNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/medications/label/{medicationName}/{dosage}", medicationName, dosageInMilligrams))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("medication-label-no-label-exists"))
                .andReturn();
    }
}

