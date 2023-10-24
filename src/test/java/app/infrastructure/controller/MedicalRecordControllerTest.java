package app.infrastructure.controller;

import app.domain.model.Person;
import app.domain.service.PersonService;
import app.infrastructure.controller.dto.medicalRecord.MedicalRecordAddRequest;
import app.infrastructure.controller.dto.medicalRecord.MedicalRecordResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {
    @Mock
    private PersonService personService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;


    @Test
    public void should_return_200_when_adding_person_medical_record_and_request_is_valid() {
        //given
        MedicalRecordAddRequest medicalRecordAddRequest = new MedicalRecordAddRequest();
        medicalRecordAddRequest.setFirstName("f1");
        medicalRecordAddRequest.setLastName("l1");
        medicalRecordAddRequest.setBirthdate("03/06/1994");
        medicalRecordAddRequest.setMedications(List.of("medication:200g"));
        medicalRecordAddRequest.setAllergies(List.of("alg"));


        Person person = new Person("f1", "l1", "03/06/1994", List.of("medication:200g"), List.of("alg"));

        when(personService.addMedicalRecordOfPerson(person)).thenReturn(person);

        //when
        final ResponseEntity<MedicalRecordResponse> result = medicalRecordController.addMedicalRecord(medicalRecordAddRequest);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        final MedicalRecordResponse expected = new MedicalRecordResponse();
        expected.setFirstName("f1");
        expected.setLastName("l1");
        expected.setBirthdate("03/06/1994");
        expected.setMedications(List.of("medication:200g"));
        expected.setAllergies(List.of("alg"));
        assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void should_return_200_when_updating_person_medical_record_and_request_is_valid() {
        //given
        MedicalRecordAddRequest medicalRecordAddRequest = new MedicalRecordAddRequest();
        medicalRecordAddRequest.setFirstName("f1");
        medicalRecordAddRequest.setLastName("l1");
        medicalRecordAddRequest.setBirthdate("03/06/1994");
        medicalRecordAddRequest.setMedications(List.of("medication:200g"));
        medicalRecordAddRequest.setAllergies(List.of("alg"));


        Person person = new Person("f1", "l1", "03/06/1994", List.of("medication:200g"), List.of("alg"));

        when(personService.updateMedicalRecordOfPerson(person)).thenReturn(person);

        //when
        final ResponseEntity<MedicalRecordResponse> result = medicalRecordController.updateMedicalRecord(medicalRecordAddRequest);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        final MedicalRecordResponse expected = new MedicalRecordResponse();
        expected.setFirstName("f1");
        expected.setLastName("l1");
        expected.setBirthdate("03/06/1994");
        expected.setMedications(List.of("medication:200g"));
        expected.setAllergies(List.of("alg"));
        assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void should_return_200_when_deleting_person_medical_record() {
        //given
        String firstname = "TestFirstname";
        String lastName = "TestLastname";

        when(personService.deleteMedicalRecordById("testfirstname.testlastname")).thenReturn(true);

        //when
        final ResponseEntity result = medicalRecordController.deleteMedicalRecord(firstname, lastName);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}