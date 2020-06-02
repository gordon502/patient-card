package com.gordon502.patientcard.model;

import java.util.List;

public class Observation {

    private final String category;
    private final String code;
    private final String date;
    private final String value;
    private List<Component> components;

    public Observation(String category, String code, String date, String value, List<Component> components) {
        this.category = category;
        this.code = code;
        this.date = date;
        this.value = value;
        this.components = components;
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

    public List<Component> getComponents() { return components; }
}
