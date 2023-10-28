package app.repository;

import app.entity.SafetyNetAlertData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class SafetyNetAlertDataJsonRepository {
    protected SafetyNetAlertData safetyNetAlertData;
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private File jsonFilePath = new File("infrastructure/src/main/resources/safetyNetAlertData.json");

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

    protected void synchronizeSafetyNetAlertData() {
        try {
            objectMapper.writeValue(jsonFilePath, safetyNetAlertData);
        } catch (IOException e) {
            throw new RuntimeException("Could not create file : " + jsonFilePath, e);
        }
    }
}
