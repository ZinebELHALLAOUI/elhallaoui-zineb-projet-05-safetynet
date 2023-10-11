package app.infrastructure.controller;

import app.domain.model.Person;
import app.domain.service.PersonService;
import app.infrastructure.controller.dto.person.ChildAlertResponse;
import app.infrastructure.controller.dto.person.PersonAddRequest;
import app.infrastructure.controller.dto.person.PersonDto;
import app.infrastructure.mapper.PersonMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        Person person = PersonMapper.requestToPerson(personAddRequest);
        Person addedPerson = personService.addPerson(person);
        PersonDto personDto = PersonMapper.personToDto(addedPerson);
        return ResponseEntity.ok(personDto);
    }

    @PutMapping("/person")
    public ResponseEntity<PersonDto> updatePerson(@RequestBody final PersonAddRequest personAddRequest) {
        Person person = PersonMapper.requestToPerson(personAddRequest);
        Person addedPerson = personService.updatePerson(person);
        PersonDto personDto = PersonMapper.personToDto(addedPerson);
        return ResponseEntity.ok(personDto);
    }


    @DeleteMapping("/person/{firstName}/{lastName}")
    public ResponseEntity deletePerson(@PathVariable final String firstName, @PathVariable final String lastName) {
        personService.deletePersonById(Person.generateIdFromFirstnameAndLastname(firstName, lastName));
        return ResponseEntity.ok().build();
    }


    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertResponse> getChildAlert(@RequestParam String address) {
        logger.info("var address : " + address);
        Set<Person> personsByAddress = personService.getPersonsByAddress(address);
        ChildAlertResponse childAlertResponse = PersonMapper.personsToChildrenAlert(personsByAddress);
        return ResponseEntity.ok(childAlertResponse);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneAlert(@RequestParam int firestation) {
        Set<Person> personsByFireStationNumber = personService.getPersonsByFireStationNumber(firestation);
        return ResponseEntity.ok(personsByFireStationNumber.stream().map(person -> person.getPhone().getNumber()).toList());
    }


}
