package app.infrastructure.controller;

import app.domain.model.FireStation;
import app.domain.model.Person;
import app.domain.service.FireStationService;
import app.domain.service.PersonService;
import app.infrastructure.controller.dto.request.FireStationRequest;
import app.infrastructure.controller.dto.response.FireStationReponse;
import app.infrastructure.controller.dto.response.PersonsByFireStationResponse;
import app.infrastructure.mapper.FireStationMapper;
import app.infrastructure.mapper.PersonMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
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
    public ResponseEntity<FireStationReponse> add(@RequestBody final FireStationRequest fireStationRequest) {
        logger.info("request :" + fireStationRequest);
        FireStation fireStation = new FireStation(fireStationRequest.getStationNumber(), fireStationRequest.getAddress());
        FireStation addedFireStation = fireStationService.add(fireStation);
        return ResponseEntity.ok(FireStationMapper.fireStationToResponse(addedFireStation));
    }

    @PutMapping
    public ResponseEntity<FireStationReponse> update(@RequestBody final FireStationRequest fireStationRequest) {
        logger.info("request :" + fireStationRequest);
        FireStation fireStation = new FireStation(fireStationRequest.getStationNumber(), fireStationRequest.getAddress());
        FireStation addedFireStation = fireStationService.update(fireStation);
        return ResponseEntity.ok(FireStationMapper.fireStationToResponse(addedFireStation));
    }


    @DeleteMapping("/{addressOrStationNumber}")
    public ResponseEntity delete(@PathVariable final String addressOrStationNumber) {
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
