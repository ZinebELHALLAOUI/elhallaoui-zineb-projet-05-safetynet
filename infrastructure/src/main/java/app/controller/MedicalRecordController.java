package app.controller;

import app.controller.dto.request.MedicalRecordAddRequest;
import app.controller.dto.response.MedicalRecordResponse;
import app.mapper.PersonMapper;
import model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.PersonService;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

    private final PersonService personService;

    public MedicalRecordController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> addMedicalRecord(@Valid @RequestBody final MedicalRecordAddRequest medicalRecordAddRequest) {
        logger.info("request :" + medicalRecordAddRequest);
        Person personWithMedicalRecord = new Person(medicalRecordAddRequest.getFirstName(), medicalRecordAddRequest.getLastName(), medicalRecordAddRequest.getBirthdate(), medicalRecordAddRequest.getMedications(), medicalRecordAddRequest.getAllergies());
        Person savedPersonWithMedicalRecord = personService.addMedicalRecordOfPerson(personWithMedicalRecord);
        MedicalRecordResponse medicalRecordResponse = PersonMapper.personToMedicalRecordResponse(savedPersonWithMedicalRecord);
        return ResponseEntity.ok(medicalRecordResponse);
    }

    @PutMapping
    public ResponseEntity<MedicalRecordResponse> updateMedicalRecord(@Valid @RequestBody final MedicalRecordAddRequest medicalRecordAddRequest) {
        logger.info("request :" + medicalRecordAddRequest);
        Person personWithMedicalRecord = new Person(medicalRecordAddRequest.getFirstName(), medicalRecordAddRequest.getLastName(), medicalRecordAddRequest.getBirthdate(), medicalRecordAddRequest.getMedications(), medicalRecordAddRequest.getAllergies());
        Person updatedPersonWithMedicalRecord = personService.updateMedicalRecordOfPerson(personWithMedicalRecord);
        MedicalRecordResponse medicalRecordResponse = PersonMapper.personToMedicalRecordResponse(updatedPersonWithMedicalRecord);
        return ResponseEntity.ok(medicalRecordResponse);
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecordResponse> deleteMedicalRecord(@Valid @PathVariable final String firstName, @PathVariable final String lastName) {
        logger.info("request firstname : " + firstName + " , lastname : " + lastName);
        personService.deleteMedicalRecordById(Person.generateIdFromFirstnameAndLastname(firstName, lastName));
        return ResponseEntity.ok().build();
    }
}
