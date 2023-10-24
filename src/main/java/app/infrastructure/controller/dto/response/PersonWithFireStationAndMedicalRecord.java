package app.infrastructure.controller.dto.response;

import java.util.List;
import java.util.Objects;

public class PersonWithFireStationAndMedicalRecord {
    private String firstname;
    private String lastname;
    private String phone;
    private int age;
    private String fireStationNumber;
    private List<String> medicalRecords;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<String> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public String getFireStationNumber() {
        return fireStationNumber;
    }

    public void setFireStationNumber(String fireStationNumber) {
        this.fireStationNumber = fireStationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonWithFireStationAndMedicalRecord that = (PersonWithFireStationAndMedicalRecord) o;
        return age == that.age && Objects.equals(firstname, that.firstname) && Objects.equals(lastname, that.lastname) && Objects.equals(phone, that.phone) && Objects.equals(fireStationNumber, that.fireStationNumber) && Objects.equals(medicalRecords, that.medicalRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, phone, age, fireStationNumber, medicalRecords);
    }
}
