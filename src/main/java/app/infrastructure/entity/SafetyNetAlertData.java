package app.infrastructure.entity;

import java.util.List;
import java.util.Objects;

public class SafetyNetAlertData {
    private List<PersonEntity> persons;
    private List<FireStationEntity> firestations;
    private List<MedicalRecordEntity> medicalrecords;

    public List<PersonEntity> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonEntity> persons) {
        this.persons = persons;
    }

    public List<FireStationEntity> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<FireStationEntity> firestations) {
        this.firestations = firestations;
    }

    public List<MedicalRecordEntity> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecordEntity> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SafetyNetAlertData that = (SafetyNetAlertData) o;
        return Objects.equals(persons, that.persons) && Objects.equals(firestations, that.firestations) && Objects.equals(medicalrecords, that.medicalrecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, firestations, medicalrecords);
    }
}
