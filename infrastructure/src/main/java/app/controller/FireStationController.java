package app.controller;

import app.controller.dto.request.FireStationRequest;
import app.controller.dto.response.FireStationReponse;
import app.controller.dto.response.PersonsByFireStationResponse;
import app.mapper.FireStationMapper;
import app.mapper.PersonMapper;
import model.FireStation;
import model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.FireStationService;
import service.PersonService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@Validated
@RequestMapping("/firestation")
public class FireStationController {

    private static final Logger logger = LogManager.getLogger(FireStationController.class);

    private final FireStationService fireStationService;
    private final PersonService personService;

    public FireStationController(FireStationService fireStationService, PersonService personService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<FireStationReponse> add(@Valid @RequestBody final FireStationRequest fireStationRequest) {
        logger.info("request :" + fireStationRequest);
        FireStation fireStation = new FireStation(fireStationRequest.getStationNumber(), fireStationRequest.getAddress());
        FireStation addedFireStation = fireStationService.add(fireStation);
        return ResponseEntity.ok(FireStationMapper.fireStationToResponse(addedFireStation));
    }

    @PutMapping
    public ResponseEntity<FireStationReponse> update(@Valid @RequestBody final FireStationRequest fireStationRequest) {
        logger.info("request :" + fireStationRequest);
        FireStation fireStation = new FireStation(fireStationRequest.getStationNumber(), fireStationRequest.getAddress());
        FireStation addedFireStation = fireStationService.update(fireStation);
        return ResponseEntity.ok(FireStationMapper.fireStationToResponse(addedFireStation));
    }


    @DeleteMapping("/{addressOrStationNumber}")
    public ResponseEntity delete(@Valid @PathVariable final String addressOrStationNumber) {
        logger.info("request addressOrStationNumber:" + addressOrStationNumber);

        try {
            final Integer fireStationNumber = Integer.valueOf(addressOrStationNumber);
            fireStationService.deleteByStationNumber(fireStationNumber);
        } catch (NumberFormatException exception) {
            final String address = addressOrStationNumber;
            fireStationService.deleteByAddress(address);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PersonsByFireStationResponse> getPersonsByFireStationNumber(@RequestParam int stationNumber) {
        logger.info("request stationNumber:" + stationNumber);
        Set<Person> personsByFireStationNumber = personService.getPersonsByFireStationNumber(stationNumber);
        PersonsByFireStationResponse personsByFireStationResponse = PersonMapper.personsToPersonsByFireStationNumber(personsByFireStationNumber);
        return ResponseEntity.ok(personsByFireStationResponse);
    }
}
