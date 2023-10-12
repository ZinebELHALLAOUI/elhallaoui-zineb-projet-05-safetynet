package app.infrastructure.controller.dto.person;

import java.util.List;

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
}
