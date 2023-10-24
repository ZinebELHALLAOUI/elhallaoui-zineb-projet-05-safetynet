package app.infrastructure.controller;

import app.domain.model.FireStation;
import app.domain.model.MedicalRecord;
import app.domain.model.Person;
import app.domain.service.PersonService;
import app.infrastructure.controller.dto.person.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @Test
    public void should_return_200_when_adding_person_and_request_is_valid() {
        //given
        final PersonAddRequest personAddRequest = new PersonAddRequest();
        personAddRequest.setFirstName("f1");
        personAddRequest.setLastName("l1");
        personAddRequest.setAddress("address1");
        personAddRequest.setCity("paris");
        personAddRequest.setEmail("test@email.com");
        personAddRequest.setZip("75000");
        personAddRequest.setPhone("841-874-7511");

        Person person = new Person("f1", "l1", "address1", "paris", "75000", "841-874-7511", "test@email.com");

        when(personService.addPerson(person)).thenReturn(person);

        //when
        final ResponseEntity<PersonDto> result = personController.addPerson(personAddRequest);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        final PersonDto expected = new PersonDto();
        expected.setFirstName("f1");
        expected.setLastName("l1");
        expected.setAddress("address1");
        expected.setPhone("841-874-7511");
        expected.setZip("75000");
        expected.setCity("paris");
        expected.setEmail("test@email.com");
        assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void should_return_200_when_updating_person_and_request_is_valid() {
        //given
        final PersonAddRequest personAddRequest = new PersonAddRequest();
        personAddRequest.setFirstName("f1");
        personAddRequest.setLastName("l1");
        personAddRequest.setAddress("address1");
        personAddRequest.setCity("paris");
        personAddRequest.setEmail("test@email.com");
        personAddRequest.setZip("75000");
        personAddRequest.setPhone("841-874-7511");

        Person person = new Person("f1", "l1", "address1", "paris", "75000", "841-874-7511", "test@email.com");

        when(personService.updatePerson(person)).thenReturn(person);

        //when
        final ResponseEntity<PersonDto> result = personController.updatePerson(personAddRequest);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        final PersonDto expected = new PersonDto();
        expected.setFirstName("f1");
        expected.setLastName("l1");
        expected.setAddress("address1");
        expected.setPhone("841-874-7511");
        expected.setZip("75000");
        expected.setCity("paris");
        expected.setEmail("test@email.com");
        assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void should_return_200_when_deleting_person() {
        //given
        String firstname = "TestFirstname";
        String lastName = "TestLastname";

        when(personService.deletePersonById("testfirstname.testlastname")).thenReturn(true);

        //when
        final ResponseEntity result = personController.deletePerson(firstname, lastName);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void should_return_200_when_getting_child_alert() {
        //given
        String address = "address";
        Person person1 = new Person("f1", "l1", "address1", "paris", "75000", "841-874-7511", "test@email.com", LocalDate.now().minusYears(4), null, null);
        Person person2 = new Person("f2", "l2", "address1", "paris", "75000", "841-874-7512", "test2@email.com", LocalDate.now().minusYears(20), null, null);
        Person person3 = new Person("f3", "l3", "address1", "paris", "75000", "841-874-7513", "test3@email.com", LocalDate.now().minusYears(19), null, null);

        when(personService.getPersonsByAddress(address)).thenReturn(Set.of(person1, person2, person3));

        //when
        final ResponseEntity<ChildAlertResponse> result = personController.getChildAlert(address);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        ChildAlertResponse expected = new ChildAlertResponse();
        ChildAlert childAlert = new ChildAlert();
        childAlert.setFirstName("f1");
        childAlert.setLastName("l1");
        childAlert.setAge(4);
        PersonDto personDto1 = new PersonDto();
        personDto1.setFirstName("f2");
        personDto1.setLastName("l2");
        personDto1.setAddress("address1");
        personDto1.setCity("paris");
        personDto1.setEmail("test2@email.com");
        personDto1.setZip("75000");
        personDto1.setPhone("841-874-7512");

        PersonDto personDto2 = new PersonDto();
        personDto2.setFirstName("f3");
        personDto2.setLastName("l3");
        personDto2.setAddress("address1");
        personDto2.setCity("paris");
        personDto2.setEmail("test3@email.com");
        personDto2.setZip("75000");
        personDto2.setPhone("841-874-7513");

        childAlert.setMembers(List.of(personDto1, personDto2));
        expected.setChildrenAlert(List.of(childAlert));
        assertThat(result.getBody()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expected);
    }

    @Test
    public void should_return_200_when_getting_phone_alert() {
        //given
        int fireStation = 10;
        Person person1 = new Person("f1", "l1", "address1", "paris", "75000", "841-874-7511", "test@email.com", LocalDate.now().minusYears(4), null, new FireStation(10, "toto"));
        Person person2 = new Person("f2", "l2", "address1", "paris", "75000", "841-874-7512", "test2@email.com", LocalDate.now().minusYears(20), null, new FireStation(10, "toto"));
        Person person3 = new Person("f3", "l3", "address1", "paris", "75000", "841-874-7513", "test3@email.com", LocalDate.now().minusYears(19), null, new FireStation(10, "toto"));

        when(personService.getPersonsByFireStationNumber(fireStation)).thenReturn(Set.of(person1, person2, person3));

        //when
        final ResponseEntity<List<String>> result = personController.getPhoneAlert(fireStation);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(List.of("841-874-7511", "841-874-7512", "841-874-7513"));
    }

    @Test
    public void should_return_200_when_getting_persons_by_address() {
        //given
        String address = "address1";
        Person person1 = new Person("f1", "l1", "address1", "paris", "75000", "841-874-7511", "test@email.com", LocalDate.now().minusYears(4), null, new FireStation(1, "toto"));
        Person person2 = new Person("f2", "l2", "address1", "paris", "75000", "841-874-7512", "test2@email.com", LocalDate.now().minusYears(20), new MedicalRecord("f2", "l2", List.of("mdct1"), List.of("alg1")), new FireStation(1, "toto"));
        Person person3 = new Person("f3", "l3", "address1", "paris", "75000", "841-874-7513", "test3@email.com", LocalDate.now().minusYears(19), new MedicalRecord("f3", "l3", List.of("mdct2", "mdct3"), List.of("alg2", "alg3")), new FireStation(2, "toto"));

        when(personService.getPersonsByAddress(address)).thenReturn(Set.of(person1, person2, person3));

        //when
        final ResponseEntity<PersonsWithFireStationAndMedicalRecordResponse> result = personController.getPersonsByAddress(address);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        PersonsWithFireStationAndMedicalRecordResponse expected = new PersonsWithFireStationAndMedicalRecordResponse();

        PersonWithFireStationAndMedicalRecord p1 = new PersonWithFireStationAndMedicalRecord();
        p1.setFirstname("f1");
        p1.setLastname("l1");
        p1.setFireStationNumber("1");
        p1.setAge(4);
        p1.setPhone("841-874-7511");
        PersonWithFireStationAndMedicalRecord p2 = new PersonWithFireStationAndMedicalRecord();
        p2.setFirstname("f2");
        p2.setLastname("l2");
        p2.setFireStationNumber("1");
        p2.setAge(20);
        p2.setPhone("841-874-7512");
        p2.setMedicalRecords(List.of("alg1", "mdct1"));
        PersonWithFireStationAndMedicalRecord p3 = new PersonWithFireStationAndMedicalRecord();
        p3.setFirstname("f3");
        p3.setLastname("l3");
        p3.setFireStationNumber("2");
        p3.setAge(19);
        p3.setPhone("841-874-7513");
        p3.setMedicalRecords(List.of("alg2", "alg3", "mdct2", "mdct3"));


        expected.setPersonsWithFireStationAndMedicalRecords(List.of(p1, p2, p3));
        assertThat(result.getBody()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expected);
    }

    @Test
    public void should_return_200_when_getting_emails_by_city() {
        //given
        String city = "Paris";
        Person person1 = new Person("f1", "l1", "address1", "Paris", "75000", "841-874-7511", "test@email.com", LocalDate.now().minusYears(4), null, new FireStation(10, "toto"));
        Person person2 = new Person("f2", "l2", "address1", "Paris", "75000", "841-874-7512", "test2@email.com", LocalDate.now().minusYears(20), null, new FireStation(10, "toto"));
        Person person3 = new Person("f3", "l3", "address1", "Paris", "75000", "841-874-7513", "test3@email.com", LocalDate.now().minusYears(19), null, new FireStation(10, "toto"));

        when(personService.getPersonsByCity(city)).thenReturn(Set.of(person1, person2, person3));

        //when
        final ResponseEntity<List<String>> result = personController.getPersonsEmailsByCity(city);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(List.of("test@email.com", "test2@email.com", "test3@email.com"));
    }

    @Test
    public void should_return_200_when_getting_persons_info() {
        //given
        String firstname = "Firstname";
        String lastname = "Lastname";
        Person person1 = new Person(firstname, lastname, "address1", "paris", "75000", "841-874-7511", "test@email.com", LocalDate.now().minusYears(4), new MedicalRecord(firstname, lastname, List.of("mdct1"), List.of("alg1")), new FireStation(1, "toto"));

        when(personService.getPersonsByFirstnameOrLastname(firstname, lastname)).thenReturn(Set.of(person1));

        //when
        final ResponseEntity<List<PersonInfo>> result = personController.getPersonsInfo(firstname, lastname);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        PersonInfo expected = new PersonInfo();
        expected.setFirstName("Firstname");
        expected.setLastName("Lastname");
        expected.setAge(4);
        expected.setAddress("address1");
        expected.setEmail("test@email.com");
        expected.setMedicalRecords(List.of("mdct1", "alg1"));

        assertThat(result.getBody()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(List.of(expected));
    }

    @Test
    public void should_return_200_when_getting_flood_stations() {

        final Set<Integer> stations = Set.of(1, 2, 3);
        Person person1 = new Person("f1", "l1", "address1", "Paris", "75000", "841-874-7511", "test@email.com", LocalDate.now().minusYears(4), new MedicalRecord("f1", "l1", List.of("mdct"), List.of("alg")), new FireStation(10, "toto"));
        Person person2 = new Person("f2", "l2", "address1", "Paris", "75000", "841-874-7512", "test2@email.com", LocalDate.now().minusYears(20), new MedicalRecord("f2", "l2", List.of("mdct"), List.of("alg")), new FireStation(10, "toto"));
        Person person3 = new Person("f3", "l3", "address2", "Paris", "75000", "841-874-7513", "test3@email.com", LocalDate.now().minusYears(19), new MedicalRecord("f3", "l3", List.of("mdct"), List.of("alg")), new FireStation(10, "toto"));

        when(personService.getPersonsByFireStationsNumbers(stations)).thenReturn(Set.of(person1, person2, person3));

        //when
        final ResponseEntity<PersonsFloodResponse> result = personController.getPersonsFlood(stations);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        PersonsFloodResponse expected = new PersonsFloodResponse();

        PersonsFloodByAddress personsFloodByAddress1 = new PersonsFloodByAddress();
        personsFloodByAddress1.setAddress("address1");
        PersonFlood personFlood1 = new PersonFlood();
        personFlood1.setFirstName("f1");
        personFlood1.setLastName("l1");
        personFlood1.setPhone("841-874-7511");
        personFlood1.setAge(4);
        personFlood1.setMedicalRecords(List.of("mdct", "alg"));

        PersonFlood personFlood2 = new PersonFlood();
        personFlood2.setFirstName("f2");
        personFlood2.setLastName("l2");
        personFlood2.setPhone("841-874-7512");
        personFlood2.setAge(20);
        personFlood2.setMedicalRecords(List.of("mdct", "alg"));
        personsFloodByAddress1.setPersonsFlood(List.of(personFlood1, personFlood2));

        PersonsFloodByAddress personsFloodByAddress2 = new PersonsFloodByAddress();
        personsFloodByAddress2.setAddress("address2");
        PersonFlood personFlood3 = new PersonFlood();
        personFlood3.setFirstName("f3");
        personFlood3.setLastName("l3");
        personFlood3.setPhone("841-874-7513");
        personFlood3.setAge(19);
        personFlood3.setMedicalRecords(List.of("mdct", "alg"));
        personsFloodByAddress2.setPersonsFlood(List.of(personFlood3));

        expected.setPersonsFloods(List.of(personsFloodByAddress1, personsFloodByAddress2));

        assertThat(result.getBody()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expected);
    }
}