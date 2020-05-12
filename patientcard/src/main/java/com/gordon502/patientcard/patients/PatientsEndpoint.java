package com.gordon502.patientcard.patients;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class PatientsEndpoint {

    @GetMapping(value = "/Patients")
    public ResponseEntity<ArrayList<Patient>> getAllPatients() {
        Patient patient = new Patient(1, "123", "123", "11-12-2020",
                                      "eldoka1", "123", false);
        ArrayList<Patient> patients = new ArrayList<>();
        patients.add(patient);
        patients.add(patient);
        return new ResponseEntity<ArrayList<Patient>>(patients, HttpStatus.OK);
    }
}
