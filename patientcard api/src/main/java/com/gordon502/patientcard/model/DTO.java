package com.gordon502.patientcard.model;

import java.util.List;

/**
 * Data Transfer Object containing requested patient
 * information + his observations and medication requests.
 */
public class DTO {
    private Patient patient;
    private List<Observation> observations;
    private List<MedicationRequest> medicationRequests;

    public DTO(Patient patient, List<Observation> observations, List<MedicationRequest> medicationRequests) {
        this.patient = patient;
        this.observations = observations;
        this.medicationRequests = medicationRequests;
    }

    public Patient getPatient() {
        return patient;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public List<MedicationRequest> getMedicationRequests() {
        return medicationRequests;
    }
}
