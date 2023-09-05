package app.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Person {

    private final String id;
    private final String firstName;
    private final String lastName;
    private String address;
    private String city;
    private String zip;
    private Phone phone;
    private Email email;
    private LocalDate birthDate;
    private String medicalRecordId;
    private List<Integer> fireStationsNumbers;

    public Person(String id, String firstName, String lastName, String address, String city, String zip, Phone phone, Email email, LocalDate birthDate, String medicalRecordId, List<Integer> fireStationsNumbers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
        this.medicalRecordId = medicalRecordId;
        this.fireStationsNumbers = fireStationsNumbers;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setMedicalRecordId(String medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public void setFireStationsNumbers(List<Integer> fireStationsNumbers) {
        this.fireStationsNumbers = fireStationsNumbers;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getMedicalRecordId() {
        return medicalRecordId;
    }

    public List<Integer> getFireStationsNumbers() {
        return fireStationsNumbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zip=" + zip +
                ", phone=" + phone +
                ", email=" + email +
                ", birthDate=" + birthDate +
                ", medicalRecordId='" + medicalRecordId + '\'' +
                ", fireStationsId=" + fireStationsNumbers +
                '}';
    }
}
