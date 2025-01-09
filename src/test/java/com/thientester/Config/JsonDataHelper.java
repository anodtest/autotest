package com.thientester.Config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

// Class read data from JSON file
public class JsonDataHelper {
    private static final String JSON_FILE_PATH ="/Users/uniscore/Documents/Project1023/mobile/qa-mobile-automation-main/src/test/resources/Live.data/SofaIncidentsMatchDetail.json";

    // Read data JSON from file and return typle JsonNode
    public static JsonNode getJsonData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(new File(JSON_FILE_PATH));
    }

    // Method get one value from JASON (if)
    public static String getValueFromJson(String key) throws IOException {
        JsonNode jsonData = getJsonData();
        return jsonData.get(key).asText();
    }
}
