package app.infrastructure.controller.dto.response;

import java.util.List;
import java.util.Objects;

public class PersonsWithFireStationAndMedicalRecordResponse {
    private List<PersonWithFireStationAndMedicalRecord> personsWithFireStationAndMedicalRecords;

    public List<PersonWithFireStationAndMedicalRecord> getPersonsWithFireStationAndMedicalRecords() {
        return personsWithFireStationAndMedicalRecords;
    }

    public void setPersonsWithFireStationAndMedicalRecords(List<PersonWithFireStationAndMedicalRecord> personsWithFireStationAndMedicalRecords) {
        this.personsWithFireStationAndMedicalRecords = personsWithFireStationAndMedicalRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonsWithFireStationAndMedicalRecordResponse that = (PersonsWithFireStationAndMedicalRecordResponse) o;
        return Objects.equals(personsWithFireStationAndMedicalRecords, that.personsWithFireStationAndMedicalRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personsWithFireStationAndMedicalRecords);
    }
}
