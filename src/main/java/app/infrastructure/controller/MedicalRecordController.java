package app.infrastructure.controller;

import app.domain.model.Person;
import app.domain.service.PersonService;
import app.infrastructure.controller.dto.medicalRecord.MedicalRecordAddRequest;
import app.infrastructure.controller.dto.medicalRecord.MedicalRecordResponse;
import app.infrastructure.mapper.PersonMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final PersonService personService;

    public MedicalRecordController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> addMedicalRecord(@RequestBody final MedicalRecordAddRequest medicalRecordAddRequest) {
        Person personWithMedicalRecord = new Person(medicalRecordAddRequest.getFirstName(), medicalRecordAddRequest.getLastName(), medicalRecordAddRequest.getBirthdate(), medicalRecordAddRequest.getMedications(), medicalRecordAddRequest.getAllergies());
        Person savedPersonWithMedicalRecord = personService.addMedicalRecordOfPerson(personWithMedicalRecord);
        MedicalRecordResponse medicalRecordResponse = PersonMapper.personToMedicalRecordResponse(savedPersonWithMedicalRecord);
        return ResponseEntity.ok(medicalRecordResponse);
    }

    @PutMapping
    public ResponseEntity<MedicalRecordResponse> updateMedicalRecord(@RequestBody final MedicalRecordAddRequest medicalRecordAddRequest) {
        Person personWithMedicalRecord = new Person(medicalRecordAddRequest.getFirstName(), medicalRecordAddRequest.getLastName(), medicalRecordAddRequest.getBirthdate(), medicalRecordAddRequest.getMedications(), medicalRecordAddRequest.getAllergies());
        Person updatedPersonWithMedicalRecord = personService.updateMedicalRecordOfPerson(personWithMedicalRecord);
        MedicalRecordResponse medicalRecordResponse = PersonMapper.personToMedicalRecordResponse(updatedPersonWithMedicalRecord);
        return ResponseEntity.ok(medicalRecordResponse);
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<MedicalRecordResponse> deleteMedicalRecord(@PathVariable final String firstName, @PathVariable final String lastName) {
        personService.deleteMedicalRecordById(Person.generateIdFromFirstnameAndLastname(firstName, lastName));
        return ResponseEntity.ok().build();
    }
}
