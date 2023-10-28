package model;


import service.exception.EntityAlreadyExistException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Person {

    private final String id;
    private final String firstName;
    private final String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private LocalDate birthdate;
    private MedicalRecord medicalRecord = null;
    private FireStation fireStation = null;

    public static final int MAJOR_AGE = 18;

    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email, LocalDate birthdate, MedicalRecord medicalRecord, FireStation fireStation) {
        this.firstName = firstName;
        this.lastName = lastName;
        if (this.firstName == null || this.lastName == null || this.firstName.isBlank() || this.lastName.isBlank())
            throw new EntityAlreadyExistException("Firstname and Lastname are required");
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.birthdate = birthdate;
        this.medicalRecord = medicalRecord;
        this.fireStation = fireStation;
        this.id = generateIdFromFirstnameAndLastname();
    }

    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
//        if (this.firstName == null || this.lastName == null || this.firstName.isBlank() || this.lastName.isBlank())
//            throw new PersonServiceRequestException("Firstname and Lastname are required");
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.id = generateIdFromFirstnameAndLastname();
    }

    public Person(final String firstName, final String lastName, final String birthdate, final List<String> medications, final List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        if (this.firstName == null || this.lastName == null || this.firstName.isBlank() || this.lastName.isBlank())
            throw new EntityAlreadyExistException("Firstname and Lastname are required");
        this.birthdate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        this.id = generateIdFromFirstnameAndLastname();
        this.medicalRecord = new MedicalRecord(id, new HashSet<>(medications), new HashSet<>(allergies));
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public FireStation getFireStation() {
        return fireStation;
    }

    public void setFireStation(FireStation fireStation) {
        this.fireStation = fireStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(address, person.address) && Objects.equals(city, person.city) && Objects.equals(zip, person.zip) && Objects.equals(phone, person.phone) && Objects.equals(email, person.email) && Objects.equals(birthdate, person.birthdate) && Objects.equals(medicalRecord, person.medicalRecord) && Objects.equals(fireStation, person.fireStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, city, zip, phone, email, birthdate, medicalRecord, fireStation);
    }

    public boolean isMinor() {
        return this.getAge() <= MAJOR_AGE;
    }

    public boolean isMajor() {
        return !this.isMinor();
    }

    public int getAge(){
        return Period.between(this.birthdate, LocalDate.now()).getYears();
    }
    public String generateIdFromFirstnameAndLastname() {
        return generateIdFromFirstnameAndLastname(this.getFirstName(), this.getLastName());
    }

    public static String generateIdFromFirstnameAndLastname(String firstName, String lastName) {
        return (firstName +
                "." +
                lastName)
                .toLowerCase(Locale.ROOT);
    }

}
