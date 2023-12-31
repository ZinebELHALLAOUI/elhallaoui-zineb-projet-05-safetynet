package app.controller;

import app.controller.dto.request.PersonAddRequest;
import app.controller.dto.response.*;
import app.mapper.PersonMapper;
import model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.PersonService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@Validated
public class PersonController {
    private static final Logger logger = LogManager.getLogger(PersonController.class);

    private final PersonService personService;


    public PersonController(final PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    public ResponseEntity<PersonDto> addPerson(@Valid @RequestBody final PersonAddRequest personAddRequest) {
        logger.info("request :" + personAddRequest);
        Person person = PersonMapper.requestToPerson(personAddRequest);
        Person addedPerson = personService.addPerson(person);
        PersonDto personDto = PersonMapper.personToDto(addedPerson);
        return ResponseEntity.ok(personDto);
    }

    @PutMapping("/person")
    public ResponseEntity<PersonDto> updatePerson(@Valid @RequestBody final PersonAddRequest personAddRequest) {
        logger.info("request :" + personAddRequest);
        Person person = PersonMapper.requestToPerson(personAddRequest);
        Person addedPerson = personService.updatePerson(person);
        PersonDto personDto = PersonMapper.personToDto(addedPerson);
        return ResponseEntity.ok(personDto);
    }


    @DeleteMapping("/person/{firstName}/{lastName}")
    public ResponseEntity deletePerson(@Valid @PathVariable final String firstName, @PathVariable final String lastName) {
        logger.info("request firstname : " + firstName + " , lastname : " + lastName);
        personService.deletePersonById(Person.generateIdFromFirstnameAndLastname(firstName, lastName));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertResponse> getChildAlert(@RequestParam String address) {
        logger.info("Requested address : " + address);
        Set<Person> personsByAddress = personService.getPersonsByAddress(address);
        ChildAlertResponse childAlertResponse = PersonMapper.personsToChildrenAlert(personsByAddress);
        return ResponseEntity.ok(childAlertResponse);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneAlert(@RequestParam int firestation) {
        logger.info("Requested firestation : " + firestation);
        Set<Person> personsByFireStationNumber = personService.getPersonsByFireStationNumber(firestation);
        return ResponseEntity.ok(personsByFireStationNumber.stream().map(person -> person.getPhone()).toList());
    }

    @GetMapping("/fire")
    public ResponseEntity<PersonsWithFireStationAndMedicalRecordResponse> getPersonsByAddress(@RequestParam String address) {
        logger.info("Requested address : " + address);
        Set<Person> personsByAddress = personService.getPersonsByAddress(address);
        PersonsWithFireStationAndMedicalRecordResponse response =
                PersonMapper.personsToPersonsWithFireStationAndMedicalRecordResponse(personsByAddress);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getPersonsEmailsByCity(@RequestParam String city) {
        logger.info("request city : " + city);
        Set<Person> personsByCity = this.personService.getPersonsByCity(city);
        return ResponseEntity.ok(PersonMapper.personsToEmails(personsByCity));
    }

    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonInfo>> getPersonsInfo(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("request firstname : " + firstName + " , lastname : " + lastName);
        Set<Person> persons = personService.getPersonsByFirstnameOrLastname(firstName, lastName);
        List<PersonInfo> personsInfo = PersonMapper.personsToPersonsInfo(persons);
        return ResponseEntity.ok(personsInfo);

    }

    @GetMapping("/flood/stations")
    public ResponseEntity<PersonsFloodResponse> getPersonsFlood(@RequestParam Set<Integer> stations) {
        logger.info("request stations : " + stations);
        Set<Person> persons = personService.getPersonsByFireStationsNumbers(stations);
        PersonsFloodResponse response = PersonMapper.personsToPersonsFloodResponse(persons);
        return ResponseEntity.ok(response);
    }

}
