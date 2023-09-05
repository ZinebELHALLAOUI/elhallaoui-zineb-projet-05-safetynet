package app.infrastructure.repository;

import app.infrastructure.entity.SafetyNetAlertData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class SafetyNetAlertDataJsonRepository {
    protected final SafetyNetAlertData safetyNetAlertData;
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private File jsonFilePath = new File("src/main/resources/safetyNetAlertData.json");

    public SafetyNetAlertDataJsonRepository() {
        SafetyNetAlertData safetyNetAlertData;
        try {
            safetyNetAlertData = objectMapper.readValue(jsonFilePath, SafetyNetAlertData.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not read file : " + jsonFilePath, e);
        }
        this.safetyNetAlertData = safetyNetAlertData;
    }

    protected SafetyNetAlertDataJsonRepository(final File jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
        SafetyNetAlertData safetyNetAlertData;
        try {
            safetyNetAlertData = objectMapper.readValue(jsonFilePath, SafetyNetAlertData.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not read file : " + jsonFilePath, e);
        }
        this.safetyNetAlertData = safetyNetAlertData;
    }
}
