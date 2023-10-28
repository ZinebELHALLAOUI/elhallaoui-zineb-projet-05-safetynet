package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.FireStationRepository;
import repository.PersonRepository;
import service.FireStationService;
import service.PersonService;
import service.implementation.FireStationServiceImpl;
import service.implementation.PersonServiceImpl;

@Configuration
public class DomainConfig {

    @Bean
    public PersonService personService(PersonRepository personRepository) {
        return new PersonServiceImpl(personRepository);
    }

     @Bean
    public FireStationService fireStationService(FireStationRepository fireStationRepository) {
        return new FireStationServiceImpl(fireStationRepository);
    }

}
