package com.gordon502.patientcard.controller.fulldata;

import com.fasterxml.jackson.databind.JsonNode;
import com.gordon502.patientcard.controller.JsonUtils;
import com.gordon502.patientcard.model.Component;
import com.gordon502.patientcard.model.DTO;
import com.gordon502.patientcard.model.MedicationRequest;
import com.gordon502.patientcard.model.Observation;
import org.checkerframework.common.value.qual.ArrayLenRange;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FulldataService {

    public DTO getDetailsFromFHIRServer(String server_addr, String id) {
        String observationURL = server_addr + "/Observation?patient=" + id;
        String medicationRequestURL = server_addr + "/MedicationRequest?patient=" + id;

        List<Observation> observations = getObservations(observationURL);
        List<MedicationRequest> requests = getMedicationRequests(medicationRequestURL);

        DTO dto = new DTO(null, observations, requests);

        return dto;
    }

    private List<Observation> getObservations(String url) {
        List<Observation> observations = new ArrayList<>();
        JsonNode jsonResponse = JsonUtils.readJSONFromServer(url);

        var total = jsonResponse.get("total").asInt();

        /**
         * Bundle contains maximum 10 elements
         * each one has link to previous/next bundle
         * of results.
         */

        for (int obs = 0; obs < total; obs++) {
            JsonNode observation = jsonResponse.get("entry").get(obs % 10).get("resource");

            var category = observation.get("category").get(0).get("coding").get(0).get("display").asText();
            var code = observation.get("code").get("text").asText();
            var date = observation.get("effectiveDateTime").asText();

            String value = null;
            if (observation.get("valueQuantity") != null) {
                value = observation.get("valueQuantity").get("value").asText() + " "
                        + observation.get("valueQuantity").get("unit").asText();
            }

            List<Component> components = null;
            if (observation.get("component") != null) { //if component is present
                System.out.println("ckomedasp");
                components = new ArrayList<>();
                System.out.println(observation.get("component"));
                for (JsonNode jsonNode : observation.findValues("component").get(0)) {
                    var text = jsonNode.get("code").get("text").asText();
                    var value2 = jsonNode.get("valueQuantity").get("value").asText() + " "
                            + jsonNode.get("valueQuantity").get("unit").asText();
                    components.add(new Component(text, value2));
                }
            }

            observations.add(new Observation(category, code, date, value, components));

            //last element of current bundle
            if (obs % 10 == 9 && obs != total - 1) {
                for (JsonNode jsonNode : jsonResponse.findValues("link").get(0)) {
                    if (jsonNode.get("relation").asText().equals("next")) {
                        var nextURL = jsonNode.get("url").asText();
                        jsonResponse = JsonUtils.readJSONFromServer(nextURL);
                        break;
                    }
                }
            }
        }

        return observations;
    }

    private List<MedicationRequest> getMedicationRequests(String url) {
        List<MedicationRequest> requests = new ArrayList<>();
        JsonNode jsonResponse = JsonUtils.readJSONFromServer(url);

        var total = jsonResponse.get("total").asInt(); //number of records in json

        for (int req = 0; req < total; req++) {
            JsonNode request = jsonResponse.get("entry").get(req % 10).get("resource");

            var status = request.get("status").asText();
            var intent = request.get("intent").asText();
            var authoredOn = request.get("authoredOn").asText();
            var text = request.get("medicationCodeableConcept").get("text").asText();

            String dosageInstruction = null;
            if (request.get("dosageInstruction") != null) {
                dosageInstruction = request.get("dosageInstruction").get(0).get("sequence").asText() + " - " +
                        request.get("dosageInstruction").get(0).get("asNeededBoolean").asText();
            }

            requests.add(new MedicationRequest(status, intent, text, authoredOn, dosageInstruction));

            //last element of current bundle
            if (req % 10 == 9 && req != total - 1) {
                for (JsonNode jsonNode : jsonResponse.findValues("link").get(0)) {
                    if (jsonNode.get("relation").asText().equals("next")) {
                        var nextURL = jsonNode.get("url").asText();
                        jsonResponse = JsonUtils.readJSONFromServer(nextURL);
                        break;
                    }
                }
            }
        }

        return requests;
    }
}