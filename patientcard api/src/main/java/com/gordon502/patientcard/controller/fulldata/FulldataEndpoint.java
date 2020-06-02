package com.gordon502.patientcard.controller.fulldata;

import com.gordon502.patientcard.model.DTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FulldataEndpoint {

    private FulldataService service;
    private final String SERVER_ADDR = "http://localhost:8080/baseDstu3";

    public FulldataEndpoint(FulldataService service) {
        this.service = service;
    }

    @GetMapping(value = "/fulldata/{id}")
    public ResponseEntity<DTO> getFullData(@PathVariable(value = "id") String id) {

        return ResponseEntity.ok(service.getDetailsFromFHIRServer(SERVER_ADDR, id));
    }

}
