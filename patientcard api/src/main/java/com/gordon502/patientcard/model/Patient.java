package com.gordon502.patientcard.model;

import java.util.Date;

/**
 * Response entity for single patient including only
 * most needed informations.
 */
public class Patient {
    private String id;
    private String name;
    private String gender;
    private String birthDate;
    private String address;
    private String telecom;

    public Patient(String id, String name, String gender, String birthDate, String address,
                   String telecom) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.telecom = telecom;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public String getTelecom() {
        return telecom;
    }

}
