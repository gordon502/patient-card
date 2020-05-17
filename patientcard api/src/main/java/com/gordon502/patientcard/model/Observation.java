package com.gordon502.patientcard.model;

public class Observation {

    private final String category;
    private final String code;
    private final String date;
    private final String value;

    public Observation(String category, String code, String date, String value) {
        this.category = category;
        this.code = code;
        this.date = date;
        this.value = value;
    }


    public String getCategory() {
        return category;
    }

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public String getValue() {
        return value;
    }
}
