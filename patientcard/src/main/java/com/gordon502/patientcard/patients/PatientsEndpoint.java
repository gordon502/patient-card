package com.gordon502.patientcard.patients;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class PatientsEndpoint {

    private PatientsService service;
    private final String SERVER_ADDR = "http://localhost:8080/baseDstu3";

    public PatientsEndpoint(PatientsService service) {
        this.service = service;
    }

    @GetMapping(value = "/Patients")
    public ResponseEntity<ArrayList<Patient>> getAllPatients() {
        return new ResponseEntity<ArrayList<Patient>>(
                service.getPatientsFromFHIRServer(SERVER_ADDR),
                HttpStatus.OK);
    }
}
