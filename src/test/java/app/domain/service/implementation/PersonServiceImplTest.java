package app.domain.service.implementation;

import app.domain.model.FireStation;
import app.domain.model.Person;
import app.domain.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    public void should_return_only_persons_with_fire_station_number_3() {
        //given
        Mockito.when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person(null,null,null,null,null,null,null,null,null,new FireStation(1,"bar")),
                        new Person(null,null,null,null,null,null,null,null,null,new FireStation(3,"toto")),
                        new Person(null,null,null,null,null,null,null,null,null,new FireStation(3,"foo"))
                )
        );

        //when
        Set<Person> result = personService.getPersonsByFireStationNumber(3);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.stream().map(person -> person.getFireStation().getStationNumber()).toList()).allMatch(number -> number.equals(3));
    }

    @Test
    public void should_not_return_any_person_when_no_person_has_fire_station_number_3() {
        //given
        Mockito.when(personRepository.getAll()).thenReturn(
                List.of(
                        new Person(null,null,null,null,null,null,null,null,null,new FireStation(1,"bar")),
                        new Person(null,null,null,null,null,null,null,null,null,new FireStation(2,"toto")),
                        new Person(null,null,null,null,null,null,null,null,null,new FireStation(6,"foo"))
                )
        );

        //when
        Set<Person> result = personService.getPersonsByFireStationNumber(3);

        //then
        assertThat(result).isEmpty();
    }
}