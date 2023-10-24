package app.infrastructure.controller.dto.person;

import java.util.List;

public class PersonsWithFireStationAndMedicalRecordResponse {
    private List<PersonWithFireStationAndMedicalRecord> personsWithFireStationAndMedicalRecords;

    public List<PersonWithFireStationAndMedicalRecord> getPersonsWithFireStationAndMedicalRecords() {
        return personsWithFireStationAndMedicalRecords;
    }

    public void setPersonsWithFireStationAndMedicalRecords(List<PersonWithFireStationAndMedicalRecord> personsWithFireStationAndMedicalRecords) {
        this.personsWithFireStationAndMedicalRecords = personsWithFireStationAndMedicalRecords;
    }

    @Override
    public String toString() {
        return "PersonsWithFireStationAndMedicalRecordResponse{" +
                "personsWithFireStationAndMedicalRecords=" + personsWithFireStationAndMedicalRecords +
                '}';
    }
}
