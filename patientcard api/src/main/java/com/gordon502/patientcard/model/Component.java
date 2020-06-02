package com.gordon502.patientcard.model;

public class Component {
    private String text;
    private String value;


    public Component(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
