package com.gordon502.patientcard.model;

public class MedicationRequest {
    private final String status;
    private final String intent;
    private final String text;
    private final String date;
    private final String dosageInstruction;

    public MedicationRequest(String status, String intent, String text,
                             String date, String dosageInstruction) {
        this.status = status;
        this.intent = intent;
        this.text = text;
        this.date = date;
        this.dosageInstruction = dosageInstruction;
    }

    public String getStatus() {
        return status;
    }

    public String getIntent() {
        return intent;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getDosageInstruction() {
        return dosageInstruction;
    }
}
