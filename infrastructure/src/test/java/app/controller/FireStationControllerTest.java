package app.controller;

import app.controller.dto.request.FireStationRequest;
import app.controller.dto.response.FireStationReponse;
import app.controller.dto.response.PersonByFireStation;
import app.controller.dto.response.PersonsByFireStationResponse;
import model.FireStation;
import model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.FireStationService;
import service.PersonService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationControllerTest {

    @Mock
    private FireStationService fireStationService;

    @Mock
    private PersonService personService;

    @InjectMocks
    private FireStationController fireStationController;


    @Test
    public void should_return_200_when_adding_fire_station_and_request_is_valid() {
        //given
        final FireStationRequest fireStationRequest = new FireStationRequest();
        fireStationRequest.setStationNumber(100);
        fireStationRequest.setAddress("test");

        final FireStation fireStation = new FireStation(100, "test");
        when(fireStationService.add(fireStation)).thenReturn(fireStation);

        //when
        final ResponseEntity<FireStationReponse> result = fireStationController.add(fireStationRequest);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        final FireStationReponse expected = new FireStationReponse();
        expected.setAddress("test");
        expected.setStationNumber(100);
        assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void should_return_200_when_updating_fire_station_and_request_is_valid() {
        //given
        final FireStationRequest fireStationRequest = new FireStationRequest();
        fireStationRequest.setStationNumber(100);
        fireStationRequest.setAddress("test");

        FireStation fireStation = new FireStation(100, "test");
        when(fireStationService.update(fireStation)).thenReturn(fireStation);

        //when
        final ResponseEntity<FireStationReponse> result = fireStationController.update(fireStationRequest);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        final FireStationReponse expected = new FireStationReponse();
        expected.setAddress("test");
        expected.setStationNumber(100);
        assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void should_return_200_when_deleting_fire_station_by_number() {
        //given
        final String addressOrStationNumber = "100";
        when(fireStationService.deleteByStationNumber(100)).thenReturn(true);

        //when
        final ResponseEntity result = fireStationController.delete(addressOrStationNumber);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void should_return_200_when_deleting_fire_station_by_address() {
        //given
        final String addressOrStationNumber = "test";
        when(fireStationService.deleteByAddress("test")).thenReturn(true);

        //when
        final ResponseEntity result = fireStationController.delete(addressOrStationNumber);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void should_return_200_when_getting_persons_by_fire_station_number() {
        //given
        final int stationNumber = 10;
        Person person1 = new Person("f1", "l1", "address1", "paris", "75000", "841-874-7511", "test@email.com", LocalDate.now(), null, null);
        Person person2 = new Person("f2", "l2", "address2", "paris", "75000", "841-874-7512", "test@email.com", LocalDate.now().minusYears(20), null, null);
        Person person3 = new Person("f3", "l3", "address3", "paris", "75000", "841-874-7513", "test@email.com", LocalDate.now().minusYears(19), null, null);
        when(personService.getPersonsByFireStationNumber(10)).thenReturn(Set.of(person1, person2, person3));

        //when
        final ResponseEntity<PersonsByFireStationResponse> result = fireStationController.getPersonsByFireStationNumber(stationNumber);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        PersonsByFireStationResponse expected = new PersonsByFireStationResponse();
        PersonByFireStation personFireStation1 = new PersonByFireStation();
        personFireStation1.setAddress("address1");
        personFireStation1.setFirstName("f1");
        personFireStation1.setLastName("l1");
        personFireStation1.setPhone("841-874-7511");

        PersonByFireStation personFireStation2 = new PersonByFireStation();
        personFireStation2.setAddress("address2");
        personFireStation2.setFirstName("f2");
        personFireStation2.setLastName("l2");
        personFireStation2.setPhone("841-874-7512");

        PersonByFireStation personFireStation3 = new PersonByFireStation();
        personFireStation3.setAddress("address3");
        personFireStation3.setFirstName("f3");
        personFireStation3.setLastName("l3");
        personFireStation3.setPhone("841-874-7513");

        expected.setPersons(List.of(personFireStation1, personFireStation2, personFireStation3));
        expected.setNumberOfAdults(2);
        expected.setNumberOfMinors(1);
        assertThat(result.getBody()).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expected);
    }

}