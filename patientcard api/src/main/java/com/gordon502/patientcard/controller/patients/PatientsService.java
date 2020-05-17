package com.gordon502.patientcard.controller.patients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gordon502.patientcard.model.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


@Service
public class PatientsService {

    public ArrayList<Patient> getPatientsFromFHIRServer(String server_addr, String family) {
        String Url = server_addr + "/Patient?_sort=_id&_pretty=true";

        //if request param was passed
        if (family != null) {
            Url += "&family=" + family;
        }

        JsonNode jsonResponse = readJSONFromServer(Url);

        //empty FHIR server
        if (jsonResponse == null) {
            return null;
        }

        ArrayList<Patient> patients = new ArrayList<>();

        int numberOfPatients = jsonResponse.get("total").asInt();

        for (int i = 0; i < numberOfPatients; i++) {
            JsonNode patient = jsonResponse.get("entry").get(i).get("resource");

            /**
             * extracting demanded information from FHIR JSON response
             */
            var id = patient.get("id").asText();
            var familyName = patient.get("name").get(0).get("family").asText();
            var givenName =  patient.get("name").get(0).get("given").get(0).asText();
            var gender = patient.get("gender").asText();
            var birthDate = patient.get("birthDate").asText();
            var address = patient.get("address").get(0).get("city").asText();
            var telecom = patient.get("telecom").get(0).get("value").asText();

            patients.add(new Patient(
                    id,
                    familyName + " " + givenName,
                    gender,
                    birthDate,
                    address,
                    telecom
            ));
        }

        return patients;
    }

    private JsonNode readJSONFromServer(String server_addr) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(server_addr, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response.getBody());
            return root;
        }
        catch (JsonProcessingException e) { return null; }

    }
}
