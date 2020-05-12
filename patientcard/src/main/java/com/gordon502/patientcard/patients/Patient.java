package com.gordon502.patientcard.patients;

import java.util.Date;

/**
 * Response entity for single patient including only
 * most needed informations.
 */
public class Patient {
    private int id;
    private String name;
    private String gender;
    private String birthDate;
    private String address;
    private String telecom;
    private boolean active;

    public Patient(int id, String name, String gender, String birthDate, String address,
                   String telecom, boolean active) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.active = active;
    }

    public int getId() {
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

    public boolean isActive() {
        return active;
    }



}
