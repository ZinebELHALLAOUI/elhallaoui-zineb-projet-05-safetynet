package app.repository;

import model.FireStation;
import model.MedicalRecord;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PersonJsonRepositoryTest {
    private final String jsonData = "{\"persons\": [{\"firstName\": \"John\", \"lastName\": \"Boyd\", \"address\": \"1509 Culver St\", \"city\": \"Culver\", \"zip\": \"97451\", \"phone\": \"841-874-6512\", \"email\": \"jaboyd@email.com\"}, {\"firstName\": \"Jacob\", \"lastName\": \"Boyd\", \"address\": \"1509 Culver St\", \"city\": \"Culver\", \"zip\": \"97451\", \"phone\": \"841-874-6513\", \"email\": \"drk@email.com\"}], \"firestations\": [{\"address\": \"1509 Culver St\", \"station\": \"3\"}], \"medicalrecords\": [{\"firstName\": \"John\", \"lastName\": \"Boyd\", \"birthdate\": \"03/06/1984\", \"medications\": [\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\": [\"nillacilan\"]}, {\"firstName\": \"Jacob\", \"lastName\": \"Boyd\", \"birthdate\": \"03/06/1989\", \"medications\": [\"pharmacol:5000mg\", \"terazine:10mg\", \"noznazol:250mg\"], \"allergies\": []}]}";
    private File jsonFilePath;

    private PersonJsonRepository personJsonRepository;

    @BeforeEach
    public void beforeEach() {
        jsonFilePath = new File("src/test/resources/tmptFile.json");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFilePath))) {
            writer.write(jsonData);
        } catch (IOException e) {
        }
        personJsonRepository = new PersonJsonRepository(jsonFilePath);
    }

    @AfterEach
    public void afterEach() {
        jsonFilePath.delete();
    }

    @Test
    public void should_get_all_persons() {
        //given : jsonData


        //when
        List<Person> result = personJsonRepository.getAll();

        //then
        List<Person> expected = List.of(new Person(
                        "John",
                        "Boyd",
                        "1509 Culver St",
                        "Culver",
                        "97451",
                        "841-874-6512",
                        "jaboyd@email.com",
                        LocalDate.parse("03/06/1984", DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                        new MedicalRecord("john.boyd", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")),
                        new FireStation(3, "1509 Culver St")
                ), new Person(
                        "Jacob",
                        "Boyd",
                        "1509 Culver St",
                        "Culver",
                        "97451",
                        "841-874-6513",
                        "drk@email.com",
                        LocalDate.parse("03/06/1989", DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                        new MedicalRecord("jacob.boyd", List.of("pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"), List.of()),
                        new FireStation(3, "1509 Culver St")
                )
        );
        assertThat(result)
                .usingRecursiveAssertion()
                .isEqualTo(expected);
    }

    @Test
    public void should_find_john_boyd() {
        //given : jsonData


        //when
        Optional<Person> result = personJsonRepository.findPersonById("john.boyd");

        //then
        Person expected = new Person(
                "John",
                "Boyd",
                "1509 Culver St",
                "Culver",
                "97451",
                "841-874-6512",
                "jaboyd@email.com",
                LocalDate.parse("03/06/1984", DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                new MedicalRecord("john.boyd", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")),
                new FireStation(3, "1509 Culver St")
        );
        assertThat(result.get())
                .usingRecursiveAssertion()
                .isEqualTo(expected);
    }

    @Test
    public void should_not_find_unknown() {
        //given : jsonData


        //when
        Optional<Person> result = personJsonRepository.findPersonById("unknown");

        //then

        assertThat(result).isEmpty();
    }

    @Test
    public void should_update_john_boyd() {
        //given : jsonData
        Person personToUpdate = new Person("John",
                "Boyd",
                "1509 Culver St",
                "Culver",
                "97451",
                "000-000-000",
                "jaboyd@yahoo.com",
                LocalDate.parse("03/06/1984", DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                new MedicalRecord("john.boyd", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")),
                new FireStation(3, "1509 Culver St")
        );

        //when
        personJsonRepository.updatePerson(personToUpdate);

        //then
        Person expected = new Person(
                "John",
                "Boyd",
                "1509 Culver St",
                "Culver",
                "97451",
                "000-000-000",
                "jaboyd@yahoo.com",
                LocalDate.parse("03/06/1984", DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                new MedicalRecord("john.boyd", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")),
                new FireStation(3, "1509 Culver St")
        );
        assertThat(personJsonRepository.findPersonById("john.boyd").get())
                .usingRecursiveAssertion()
                .isEqualTo(expected);
    }


    @Test
    public void should_add_a_person() {
        //given : jsonData
        Person person = new Person(
                "Firstname",
                "Lastname",
                "1509 Culver St",
                "Culver",
                "97451",
                "000-000-000",
                "test@yahoo.com",
                LocalDate.parse("03/06/1984", DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                new MedicalRecord("firstname.lastname", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")),
                new FireStation(3, "1509 Culver St")
        );

        //when
        personJsonRepository.addPerson(person);

        //then
        Person expected = new Person(
                "Firstname",
                "Lastname",
                "1509 Culver St",
                "Culver",
                "97451",
                "000-000-000",
                "test@yahoo.com",
                null,
                null,
                new FireStation(3, "1509 Culver St")
        );
        assertThat(personJsonRepository.findPersonById("firstname.lastname").get())
                .usingRecursiveAssertion()
                .isEqualTo(expected);
    }

    @Test
    public void should_delete_a_person() {
        //given : jsonData

        //when
        personJsonRepository.deletePersonById("john.boyd");

        //then
        assertThat(personJsonRepository.findPersonById("john.boyd")).isEmpty();
    }

    @Test
    public void should_return_false_when_deleting_not_existing_a_person() {
        //given : jsonData

        //when
        boolean result = personJsonRepository.deletePersonById("unknown.boyd");

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void should_add_medical_record() {
        //given : jsonData
        Person personWithMedicalRecord = new Person(
                "Firstname",
                "Lastname",
                "1509 Culver St",
                "Culver",
                "97451",
                "841-874-6512",
                "jaboyd@email.com",
                LocalDate.parse("03/06/1984", DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                new MedicalRecord("firstname.lastname", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")),
                new FireStation(3, "1509 Culver St")
        );

        //when
        personJsonRepository.addMedicalRecordOfPerson(personWithMedicalRecord);

        //then
        assertThat(personJsonRepository.isExistsMedicalRecordById("firstname.lastname")).isTrue();
    }

    @Test
    public void should_return_true_when_medical_record_exist() {
        //given : jsonData

        //when
        boolean result = personJsonRepository.isExistsMedicalRecordById("john.boyd");

        //then
        assertThat(result).isTrue();
    }

    @Test
    public void should_return_false_when_medical_record_does_not_exist() {
        //given : jsonData

        //when
        boolean result = personJsonRepository.isExistsMedicalRecordById("john.unknown");

        //then
        assertThat(result).isFalse();
    }

    @Test
    public void should_update_person_medical_record() {
        //given : jsonData
        Person personWithMedicalRecordToUpdate = new Person(
                "John",
                "Boyd",
                "1509 Culver St",
                "Culver",
                "97451",
                "841-874-6512",
                "jaboyd@email.com",
                LocalDate.parse("03/06/1984", DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                new MedicalRecord("john.boyd", List.of("test:350mg"), List.of("Test")),
                new FireStation(3, "1509 Culver St")
        );

        //when
        Person result = personJsonRepository.updateMedicalRecordOfPerson(personWithMedicalRecordToUpdate);

        //then
        Person expected = new Person(
                "John",
                "Boyd",
                "1509 Culver St",
                "Culver",
                "97451",
                "841-874-6512",
                "jaboyd@email.com",
                LocalDate.parse("03/06/1984", DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                new MedicalRecord("john.boyd", List.of("test:350mg"), List.of("Test")),
                new FireStation(3, "1509 Culver St")
        );
        assertThat(result)
                .usingRecursiveAssertion()
                .isEqualTo(expected);
    }

    @Test
    public void should_delete_medical_record() {
        //given : jsonData

        //when
        boolean result = personJsonRepository.deleteMedicalRecordOfPersonById("john.boyd");

        //then
        assertThat(result).isTrue();
        assertThat(personJsonRepository.isExistsMedicalRecordById("john.boyd")).isFalse();
    }

    @Test
    public void should_return_false_when_delete_a_not_existing_medical_record() {
        //given : jsonData

        //when
        boolean result = personJsonRepository.deleteMedicalRecordOfPersonById("unknown.boyd");

        //then
        assertThat(result).isFalse();
    }

}