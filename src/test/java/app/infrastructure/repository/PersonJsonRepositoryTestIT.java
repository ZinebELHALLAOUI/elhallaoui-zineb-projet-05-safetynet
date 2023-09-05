package app.infrastructure.repository;

import app.domain.model.Email;
import app.domain.model.Person;
import app.domain.model.Phone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PersonJsonRepositoryTestIT {

    private final File jsonFilePath = new File("src/test/resources/safetyNetAlertSampleJsonFiles/sampleJsonSafetyNetAlert1.json");
    private final PersonJsonRepository personJsonRepository = new PersonJsonRepository(jsonFilePath);

    @Test
    @DisplayName("Doit verifier la recuperation et la conversion d'une person entity vers person")
    public void should_get_all_persons(){
        //given : sampleJsonSafetyNetAlert1.json


        //when
        List<Person> result = personJsonRepository.getAll();

        //then
        List<Person> expected = List.of(new Person(
                "john.boyd",
                "John",
                "Boyd",
                "1509 Culver St",
                "Culver",
                "97451",
                new Phone("841-874-6512"),
                new Email("jaboyd@email.com"),
                LocalDate.parse("03/06/1984", DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                "john.boyd",
                List.of(3)
        ));
        assertThat(result)
                .usingRecursiveAssertion()
                .isEqualTo(expected);
    }

}